package org.ikigaidigital.domain.service;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.springframework.stereotype.Component;

@Component
public class StudentInterestStrategy implements InterestStrategy {

    private static final Double INTEREST_RATE = 0.03;
    private static final Double NO_INTEREST = 0.0;

    private static final Integer MIN_NUM_DAYS = 30;
    private static final Integer MAX_NUM_DAYS = 366;
    private static final Integer MONTHS_PER_YEAR = 12;

    @Override
    public PlanType supports() {
        return PlanType.STUDENT;
    }

    @Override
    public Double calculateInterest(TimeDeposit deposit) {
        if (deposit.getDays() <= MIN_NUM_DAYS) {
            return NO_INTEREST;
        }

        if (deposit.getDays() >= MAX_NUM_DAYS) {
            return NO_INTEREST;
        }

        return deposit.getBalance() * INTEREST_RATE / MONTHS_PER_YEAR;
    }
}