CREATE TABLE time_deposits (
                            id SERIAL PRIMARY KEY,
                            plan_type VARCHAR(50) NOT NULL,
                            days INTEGER NOT NULL,
                            balance DECIMAL(15, 2) NOT NULL
);

CREATE TABLE withdrawals (
                            id SERIAL PRIMARY KEY,
                            time_deposit_id INTEGER NOT NULL,
                            amount DECIMAL(15, 2) NOT NULL,
                            transaction_date DATE NOT NULL,

                            CONSTRAINT fk_deposit
                                FOREIGN KEY(time_deposit_id)
                                REFERENCES time_deposits(id)
);
