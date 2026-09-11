CREATE TABLE tb_matches
(
    id            VARCHAR NOT NULL,
    liker_id      VARCHAR NOT NULL,
    liked_id      VARCHAR NOT NULL,
    status        VARCHAR NOT NULL,
    unmatched_at  TIMESTAMP WITHOUT TIME ZONE,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT    pk_tb_match_requests PRIMARY KEY (id),
    CONSTRAINT    fk_tb_match_requests_liker_id_crushes FOREIGN KEY (liker_id) REFERENCES tb_crushes (id) ON DELETE CASCADE,
    CONSTRAINT    fk_tb_match_requests_liked_id_crushes FOREIGN KEY (liked_id) REFERENCES tb_crushes (id) ON DELETE CASCADE,
    CONSTRAINT    no_self_like CHECK (liker_id <> liked_id),
    CONSTRAINT    unique_like UNIQUE (liker_id, liked_id)
);