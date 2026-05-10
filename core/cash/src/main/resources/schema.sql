CREATE TABLE IF NOT EXISTS transactions (
    id UUID,
    subject varchar(255) NOT NULL,
    amount int NOT NULL,
	CONSTRAINT pk_transactions PRIMARY KEY (id)
);