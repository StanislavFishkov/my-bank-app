CREATE TABLE IF NOT EXISTS accounts (
    id UUID,
    subject varchar(255) NOT NULL,
    login varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    balance int NOT NULL DEFAULT 0,
    birthdate date,
    email varchar(255),
	CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_subject UNIQUE (subject),
    CONSTRAINT uq_users_login UNIQUE (login)
);