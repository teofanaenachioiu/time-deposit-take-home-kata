package org.ikigaidigital.unit.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.domain.service.BasicInterestStrategy;
import org.junit.jupiter.api.Test;

class BasicInterestStrategyTest {

	private final BasicInterestStrategy strategy = new BasicInterestStrategy();

	@Test
	void supports_shouldReturnBasicPlanType() {
		assertThat(strategy.supports()).isEqualTo(PlanType.BASIC);
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreBelowMinimum() {
		TimeDeposit deposit = new TimeDeposit(1, "basic",1200.00, 29);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("0.00");
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreExactlyMinimum() {
		TimeDeposit deposit = new TimeDeposit(2, "basic", 1200.00, 30);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("0.00");
	}

	@Test
	void calculateInterest_shouldCalculateInterest_whenDaysAreAboveMinimum() {
		TimeDeposit deposit = new TimeDeposit(3, "basic", 1200.00, 31);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("1.0");
	}

	@Test
	void calculateInterest_shouldApplyFormulaForAnyBalanceAboveMinimumDays() {
		TimeDeposit deposit = new TimeDeposit(4, "basic", 1000.00, 365);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("0.8333333333");
	}
}
