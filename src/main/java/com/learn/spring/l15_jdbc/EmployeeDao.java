package com.learn.spring.l15_jdbc;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * DAO = Data Access Object. Spring's JDBC templates remove the boilerplate of plain JDBC:
 * opening/closing connections, statements and result sets, and the checked SQLException.
 * You only write the SQL and the row mapping.
 */
@Repository
public class EmployeeDao {

    /** RowMapper: turns ONE row of the ResultSet into ONE object. */
    private static final RowMapper<Employee> ROW_MAPPER = (rs, rowNum) -> new Employee(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("department"),
            rs.getBigDecimal("salary"),
            rs.getString("email"));

    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate namedJdbc;
    private final JdbcClient client;

    public EmployeeDao(JdbcTemplate jdbc, NamedParameterJdbcTemplate namedJdbc, JdbcClient client) {
        this.jdbc = jdbc;
        this.namedJdbc = namedJdbc;
        this.client = client;
    }

    // ---------- JdbcTemplate ----------

    public int count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM employee", Integer.class);
    }

    public List<Employee> findAll() {
        return jdbc.query("SELECT * FROM employee ORDER BY id", ROW_MAPPER);
    }

    /** queryForObject throws EmptyResultDataAccessException when no row matches. */
    public Employee getById(long id) {
        return jdbc.queryForObject("SELECT * FROM employee WHERE id = ?", ROW_MAPPER, id);
    }

    public int raiseSalaries(String department, int percent) {
        return jdbc.update("UPDATE employee SET salary = salary * (100 + ?) / 100 WHERE department = ?",
                percent, department);
    }

    public boolean deleteById(long id) {
        return jdbc.update("DELETE FROM employee WHERE id = ?", id) == 1;
    }

    /** Batch: many inserts in one round-trip, much faster than a loop of single inserts. */
    public int batchInsert(List<Employee> employees) {
        int[][] counts = jdbc.batchUpdate(
                "INSERT INTO employee (name, department, salary, email) VALUES (?, ?, ?, ?)",
                employees, 50, (ps, e) -> {
                    ps.setString(1, e.name());
                    ps.setString(2, e.department());
                    ps.setBigDecimal(3, e.salary());
                    ps.setString(4, e.email());
                });
        int total = 0;
        for (int[] batch : counts) {
            total += batch.length;
        }
        return total;
    }

    /** ResultSetExtractor: you control the WHOLE result set, e.g. to build a map. */
    public Map<String, BigDecimal> averageSalaryByDepartment() {
        return jdbc.query("SELECT department, AVG(salary) AS avg_salary FROM employee GROUP BY department ORDER BY department",
                rs -> {
                    Map<String, BigDecimal> result = new LinkedHashMap<>();
                    while (rs.next()) {
                        result.put(rs.getString("department"), rs.getBigDecimal("avg_salary"));
                    }
                    return result;
                });
    }

    // ---------- NamedParameterJdbcTemplate ----------

    public List<Employee> findByDepartmentEarningAtLeast(String department, BigDecimal minSalary) {
        var params = new MapSqlParameterSource()
                .addValue("dept", department)
                .addValue("min", minSalary);
        return namedJdbc.query(
                "SELECT * FROM employee WHERE department = :dept AND salary >= :min ORDER BY salary DESC",
                params, ROW_MAPPER);
    }

    // ---------- JdbcClient (recommended for new code) ----------

    /** query(Employee.class) maps columns to record components by name; no RowMapper needed. */
    public Optional<Employee> findById(long id) {
        return client.sql("SELECT * FROM employee WHERE id = :id")
                .param("id", id)
                .query(Employee.class)
                .optional();
    }

    /** Inserts and returns the employee with the database-generated id. */
    public Employee insert(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        client.sql("INSERT INTO employee (name, department, salary, email) VALUES (:name, :department, :salary, :email)")
                .param("name", employee.name())
                .param("department", employee.department())
                .param("salary", employee.salary())
                .param("email", employee.email())
                .update(keyHolder, "id");
        long id = keyHolder.getKey().longValue();
        return new Employee(id, employee.name(), employee.department(), employee.salary(), employee.email());
    }

    public List<String> namesInDepartment(String department) {
        return client.sql("SELECT name FROM employee WHERE department = ? ORDER BY name")
                .param(department)
                .query(String.class)
                .list();
    }
}
