INSERT INTO time_deposits (plan_type, days, balance) VALUES
('BASIC',   20,  1000.00),
('STUDENT', 100, 2000.00),
('PREMIUM', 50,  5000.00),
('STUDENT', 400, 3000.00),
('BASIC',   60,  1500.00);


INSERT INTO withdrawals (time_deposit_id, amount, transaction_date) VALUES
-- deposit 1 (0 withdrawals)

-- deposit 2 (2 withdrawals)
(2, 150.00, '2026-01-10'),
(2, 200.00, '2026-02-15'),

-- deposit 3 (3 withdrawals)
(3, 500.00, '2026-01-05'),
(3, 300.00, '2026-02-01'),
(3, 250.00, '2026-02-20'),

-- deposit 4 (1 withdrawal)
(4, 400.00, '2026-01-25');

-- deposit 5 (0 withdrawals)
