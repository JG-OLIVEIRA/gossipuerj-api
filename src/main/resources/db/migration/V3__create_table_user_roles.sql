CREATE TABLE tb_user_roles (
    user_id VARCHAR NOT NULL,
    role VARCHAR NOT NULL,
    CONSTRAINT pk_tb_user_roles PRIMARY KEY (user_id, role),
    CONSTRAINT fk_tb_user_roles_user FOREIGN KEY (user_id) REFERENCES tb_users (id) ON DELETE CASCADE
);