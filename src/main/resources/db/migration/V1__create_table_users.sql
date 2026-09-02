CREATE TABLE tb_users (
    id VARCHAR(36) NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    gender VARCHAR(12) NOT NULL,
    orientation VARCHAR(13) NOT NULL,
    verification_code VARCHAR(6),
    verification_code_expires_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_tb_users PRIMARY KEY (id),
    CONSTRAINT uq_tb_users_email UNIQUE (email),
    CONSTRAINT uq_tb_users_username UNIQUE (username)
);