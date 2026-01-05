-- Создаем схему
CREATE SCHEMA IF NOT EXISTS cart;

-- Сначала создаем основную таблицу
CREATE TABLE IF NOT EXISTS carts (
    cart_id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- случайные значения
    username VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true
);

-- Затем создаем таблицу с внешним ключом
CREATE TABLE IF NOT EXISTS cart_products (
    cart_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(cart_id, product_id),
    FOREIGN KEY(cart_id) REFERENCES carts(cart_id) ON DELETE CASCADE
);
