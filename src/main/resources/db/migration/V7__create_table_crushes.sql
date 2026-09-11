CREATE TABLE tb_crushes
(
    id          VARCHAR NOT NULL,
    user_id     VARCHAR NOT NULL,
    photo_url   TEXT,
    description TEXT,
    gender      VARCHAR NOT NULL,
    orientation VARCHAR NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_tb_crushes PRIMARY KEY (id),
    CONSTRAINT fk_tb_crushes_users FOREIGN KEY (user_id) REFERENCES tb_users (id) ON DELETE CASCADE
);