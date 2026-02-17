CREATE SCHEMA IF NOT EXISTS store;

CREATE TABLE IF NOT EXISTS products (
    product_id UUID NOT NULL PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL,
    description VARCHAR(500) NOT NULL,
    image_src VARCHAR(100),
    quantity_state VARCHAR(10) NOT NULL,
    product_state VARCHAR(10) NOT NULL,
    product_category VARCHAR(10),
    price DOUBLE PRECISION NOT NULL CHECK (price >= 1)
);