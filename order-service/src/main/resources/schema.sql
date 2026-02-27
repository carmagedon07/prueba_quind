CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL,
    total_amount DECIMAL(10,2),
    status VARCHAR(50)
);