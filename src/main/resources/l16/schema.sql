CREATE TABLE account (
    id      VARCHAR(10)    PRIMARY KEY,
    owner   VARCHAR(50)    NOT NULL,
    balance DECIMAL(12, 2) NOT NULL
);

CREATE TABLE audit_log (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(200) NOT NULL
);

INSERT INTO account (id, owner, balance) VALUES ('ACC-1', 'alice', 1000.00);
INSERT INTO account (id, owner, balance) VALUES ('ACC-2', 'bob',    500.00);
