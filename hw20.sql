CREATE TABLE IF NOT EXISTS tasks (
    id bigserial NOT NULL,
		name VARCHAR(500) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (id)
);