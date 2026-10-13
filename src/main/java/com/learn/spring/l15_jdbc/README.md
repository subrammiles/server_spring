# Lesson 15: Spring JDBC

## Why not plain JDBC?
Plain JDBC needs about 20 lines per query: get a connection, prepare the statement, set parameters, loop over the
`ResultSet`, close everything in `finally`, and handle the checked `SQLException`. Spring's templates keep the
two parts that matter: **the SQL** and **the mapping**.

## Concepts
- **DataSource**: where connections come from. Here an embedded H2 database built from `schema.sql` and `data.sql`.
  In production it is a connection pool (HikariCP).
- **JdbcTemplate**: `query`, `queryForObject`, `update`, `batchUpdate` with `?` parameters.
- **NamedParameterJdbcTemplate**: `:name` parameters, which are easier to read.
- **JdbcClient** (Spring 6.1+): a fluent API (`sql(...).param(...).query(Employee.class).list()`). **Use it for new code.**
- **RowMapper** maps one row to one object. **ResultSetExtractor** maps the whole result (maps, aggregates).
- **Generated keys**: `KeyHolder` returns the auto-increment id.
- **Batch updates**: many rows in one round trip.
- **Exception translation**: vendor `SQLException`s become `DataAccessException` subclasses
  (`DuplicateKeyException`, `EmptyResultDataAccessException`...). They are unchecked and the same for every database.
- **SQL injection**: always use parameters (`?`, `:name`). Never concatenate user input into SQL.

## JDBC vs JPA (lessons 17-18)
JDBC: you write all SQL. Full control, no surprises, great for reporting and complex queries.
JPA: you work with objects and it generates SQL. Less code for CRUD, but more to understand.

## Run
```
mvn -q compile exec:java -Dexec.args=15
```
