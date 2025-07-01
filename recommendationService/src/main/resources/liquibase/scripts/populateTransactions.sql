-- liquibase formatted sql
-- changeset sryazanova:6

-- Products
INSERT INTO products (id, type) VALUES
  ('11111111-1111-1111-1111-111111111111', 'DEBIT'),
  ('22222222-2222-2222-2222-222222222222', 'CREDIT'),
  ('33333333-3333-3333-3333-333333333333', 'SAVING'),
  ('44444444-4444-4444-4444-444444444444', 'INVEST');

-- Transactions
INSERT INTO transactions (type, user_id, product_id, amount) VALUES
  ('DEPOSIT', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 100.00),
  ('DEPOSIT', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '22222222-2222-2222-2222-222222222222', 200.00),
  ('WITHDRAW', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '33333333-3333-3333-3333-333333333333', 150.00),
  ('WITHDRAW', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '44444444-4444-4444-4444-444444444444', 300.00),
  ('DEPOSIT', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '11111111-1111-1111-1111-111111111111', 150000.00),
  ('WITHDRAW', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '11111111-1111-1111-1111-111111111111', 100.00);
