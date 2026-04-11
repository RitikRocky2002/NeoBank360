CREATE DATABASE neobank_db;

CREATE USER 'neobank_user'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON neobank_db.* TO 'neobank_user'@'localhost';

USE neobank_db;

CREATE TABLE users(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','CUSTOMER') DEFAULT 'CUSTOMER',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    balance DECIMAL(15 , 2 ) DEFAULT 0.00,
    account_type ENUM('SAVINGS', 'CURRENT') NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT fk_accounts_users FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);

CREATE TABLE transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_id BIGINT,
    type ENUM('DEBIT', 'CREDIT') NOT NULL,
    amount DECIMAL(15 , 2 ) NOT NULL CHECK (amount > 0),
    description VARCHAR(500),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    balance_after DECIMAL(15 , 2 ) NOT NULL,
    CONSTRAINT fk_transactions_accounts FOREIGN KEY (account_id)
        REFERENCES accounts (id)
        ON DELETE RESTRICT
);

