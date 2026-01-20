CREATE SCHEMA IF NOT EXISTS warehouse;

CREATE TABLE IF NOT EXISTS warehouse_products (
    product_id UUID PRIMARY KEY,
    fragile BOOLEAN,
    width DOUBLE PRECISION NOT NULL,
    height DOUBLE PRECISION NOT NULL,
    depth DOUBLE PRECISION NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    quantity BIGINT
);

CREATE TABLE IF NOT EXISTS warehouse.bookings (
    booking_id UUID NOT NULL PRIMARY KEY,
    order_id UUID NOT NULL,
    delivery_id UUID
);

CREATE TABLE IF NOT EXISTS warehouse.booking_product (
    booking_id UUID NOT NULL REFERENCES warehouse.bookings(booking_id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES warehouse_products(product_id) ON DELETE CASCADE,
    quantity BIGINT NOT NULL,
    PRIMARY KEY(booking_id, product_id)
);