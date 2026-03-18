package org.ikigaidigital.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.springframework.stereotype.Component;

@Component
public class BasicInterestStrategy implements InterestStrategy {

	private static final BigDecimal INTEREST_RATE = new BigDecimal("0.01");
	private static final BigDecimal NO_INTEREST = BigDecimal.ZERO;

	private static final Integer MIN_NUM_DAYS = 30;
	private static final Integer MONTHS_PER_YEAR = 12;

	@Override
	public PlanType supports() {
		return PlanType.BASIC;
	}

	@Override
	public BigDecimal calculateInterest(TimeDeposit deposit) {
		if (deposit.getDays() <= MIN_NUM_DAYS) {
			return NO_INTEREST;
		}

		return BigDecimal.valueOf(deposit.getBalance()).multiply(INTEREST_RATE)
				.divide(BigDecimal.valueOf(MONTHS_PER_YEAR), 10, RoundingMode.HALF_UP);
	}
}
