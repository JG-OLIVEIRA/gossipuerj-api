CREATE TABLE tb_likes
(
    id         VARCHAR NOT NULL,
    author_id  VARCHAR NOT NULL,
    post_id    VARCHAR,
    comment_id VARCHAR,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_tb_likes PRIMARY KEY (id),
    CONSTRAINT fk_tb_likes_users FOREIGN KEY (author_id) REFERENCES tb_users (id) ON DELETE CASCADE,
    CONSTRAINT fk_tb_likes_posts FOREIGN KEY (post_id) REFERENCES tb_posts (id) ON DELETE CASCADE,
    CONSTRAINT fk_tb_likes_comments FOREIGN KEY (comment_id) REFERENCES tb_comments (id) ON DELETE CASCADE
);