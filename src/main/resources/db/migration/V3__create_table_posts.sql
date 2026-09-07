CREATE TABLE tb_posts
(
    id         VARCHAR NOT NULL,
    title      VARCHAR NOT NULL,
    content    VARCHAR NOT NULL,
    category   VARCHAR NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    author_id   VARCHAR NOT NULL,
    CONSTRAINT pk_tb_posts PRIMARY KEY (id),
    CONSTRAINT fk_tb_posts_users FOREIGN KEY (author_id) REFERENCES tb_users (id) ON DELETE CASCADE
);