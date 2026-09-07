CREATE TABLE tb_users (
    id VARCHAR NOT NULL,
    username VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    gender VARCHAR NOT NULL,
    orientation VARCHAR NOT NULL,
    verification_code VARCHAR,
    verification_code_expires_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_tb_users PRIMARY KEY (id),
    CONSTRAINT uq_tb_users_email UNIQUE (email),
    CONSTRAINT uq_tb_users_username UNIQUE (username)
);