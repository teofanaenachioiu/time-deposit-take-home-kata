CREATE TABLE time_deposits (
                            id INTEGER PRIMARY KEY,
                            plan_type VARCHAR(50) NOT NULL,
                            days INTEGER NOT NULL,
                            balance DOUBLE PRECISION NOT NULL
);

CREATE TABLE withdrawals (
                            id BIGSERIAL PRIMARY KEY,
                            time_deposit_id INTEGER NOT NULL,
                            amount DOUBLE PRECISION NOT NULL,
                            transaction_date DATE NOT NULL,

                            CONSTRAINT fk_deposit
                                FOREIGN KEY(time_deposit_id)
                                REFERENCES time_deposits(id)
);