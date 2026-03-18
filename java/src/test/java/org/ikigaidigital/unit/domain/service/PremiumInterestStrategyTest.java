package org.ikigaidigital.unit.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.domain.service.PremiumInterestStrategy;
import org.junit.jupiter.api.Test;

class PremiumInterestStrategyTest {

	private final PremiumInterestStrategy strategy = new PremiumInterestStrategy();

	@Test
	void supports_shouldReturnPremiumPlanType() {
		assertThat(strategy.supports()).isEqualTo(PlanType.PREMIUM);
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreBelowMinimum() {
		TimeDeposit deposit = new TimeDeposit(1, "premium", 1200.00, 44);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("0.00");
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreExactlyMinimum() {
		TimeDeposit deposit = new TimeDeposit(2, "premium", 1200.00, 45);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("0.00");
	}

	@Test
	void calculateInterest_shouldCalculateInterest_whenDaysAreAboveMinimum() {
		TimeDeposit deposit = new TimeDeposit(3, "premium", 1200.00, 46);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("5.0");
	}

	@Test
	void calculateInterest_shouldApplyFormulaForAnyBalanceAboveMinimumDays() {
		TimeDeposit deposit = new TimeDeposit(4, "premium",1000.00, 100);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("4.1666666667");
	}
}
