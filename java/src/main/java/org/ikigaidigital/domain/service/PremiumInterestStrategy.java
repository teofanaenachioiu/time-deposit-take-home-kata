package org.ikigaidigital.domain.service;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.springframework.stereotype.Component;

@Component
public class PremiumInterestStrategy implements InterestStrategy {

	private static final Double INTEREST_RATE = 0.05;
	private static final Double NO_INTEREST = 0.0;

	private static final Integer MIN_NUM_DAYS = 45;
	private static final Integer MONTHS_PER_YEAR = 12;

	@Override
	public PlanType supports() {
		return PlanType.PREMIUM;
	}

	@Override
	public Double calculateInterest(TimeDeposit deposit) {
		if (deposit.getDays() <= MIN_NUM_DAYS) {
			return NO_INTEREST;
		}

		return deposit.getBalance() * INTEREST_RATE / MONTHS_PER_YEAR;
	}
}
