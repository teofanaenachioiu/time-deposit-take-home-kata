package org.ikigaidigital.unit.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.domain.service.StudentInterestStrategy;
import org.junit.jupiter.api.Test;

class StudentInterestStrategyTest {

	private final StudentInterestStrategy strategy = new StudentInterestStrategy();

	@Test
	void supports_shouldReturnStudentPlanType() {
		assertThat(strategy.supports()).isEqualTo(PlanType.STUDENT);
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreBelowMinimum() {
		TimeDeposit deposit = new TimeDeposit(1, "student", 1200.00, 29);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("0.00");
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreExactlyMinimum() {
		TimeDeposit deposit = new TimeDeposit(2, "student", 1200.00, 30);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("0.00");
	}

	@Test
	void calculateInterest_shouldCalculateInterest_whenDaysAreInsideEligibleInterval() {
		TimeDeposit deposit = new TimeDeposit(3, "student", 1200.00, 100);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("3.0");
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreExactlyMaximum() {
		TimeDeposit deposit = new TimeDeposit(4, "student", 1200.00, 366);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("0.00");
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreAboveMaximum() {
		TimeDeposit deposit = new TimeDeposit(5, "student", 1200.00, 367);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("0.00");
	}

	@Test
	void calculateInterest_shouldApplyFormulaForAnyEligibleBalance() {
		TimeDeposit deposit = new TimeDeposit(6, "student", 1000.00, 120);

		BigDecimal interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualByComparingTo("2.5");
	}
}
