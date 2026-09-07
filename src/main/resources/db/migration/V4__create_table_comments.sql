CREATE TABLE tb_comments
(
    id         VARCHAR NOT NULL,
    content    VARCHAR NOT NULL,
    author_id  VARCHAR NOT NULL,
    post_id    VARCHAR NOT NULL,
    parent_id  VARCHAR,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_tb_comments PRIMARY KEY (id),
    CONSTRAINT fk_tb_comments_users FOREIGN KEY (author_id) REFERENCES tb_users (id) ON DELETE CASCADE,
    CONSTRAINT fk_tb_comments_posts FOREIGN KEY (post_id) REFERENCES tb_posts (id) ON DELETE CASCADE,
    CONSTRAINT fk_tb_comments_parent FOREIGN KEY (parent_id) REFERENCES tb_comments (id) ON DELETE CASCADE
);