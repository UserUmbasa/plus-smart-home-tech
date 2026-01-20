CREATE SCHEMA IF NOT EXISTS payment;

CREATE TABLE IF NOT EXISTS payment.payments (
    payment_id UUID NOT NULL PRIMARY KEY,
    total_payment NUMERIC,
    delivery_total NUMERIC,
    fee_total NUMERIC,
    product_total NUMERIC,
    payment_state VARCHAR(10) NOT NULL,
    order_id UUID NOT NULL
);