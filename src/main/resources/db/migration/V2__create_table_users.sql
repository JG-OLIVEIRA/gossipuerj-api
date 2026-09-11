CREATE TABLE tb_users (
    id VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    username VARCHAR NOT NULL,
    course_id VARCHAR,
    password VARCHAR NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    verification_code VARCHAR,
    verification_code_expires_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_tb_users PRIMARY KEY (id),
    CONSTRAINT uq_tb_users_email UNIQUE (email),
    CONSTRAINT uq_tb_users_username UNIQUE (username),
    CONSTRAINT fk_tb_users_courses FOREIGN KEY (course_id) REFERENCES tb_courses (id) ON DELETE CASCADE
);