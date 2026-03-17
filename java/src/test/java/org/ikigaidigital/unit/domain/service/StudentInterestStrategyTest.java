package org.ikigaidigital.unit.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

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
		TimeDeposit deposit = new TimeDeposit(1, "student", 1200.0, 29);

		Double interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualTo(0.0);
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreExactlyMinimum() {
		TimeDeposit deposit = new TimeDeposit(2, "student", 1200.0, 30);

		Double interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualTo(0.0);
	}

	@Test
	void calculateInterest_shouldCalculateInterest_whenDaysAreInsideEligibleInterval() {
		TimeDeposit deposit = new TimeDeposit(3, "student", 1200.0, 100);

		Double interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualTo(3.0);
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreExactlyMaximum() {
		TimeDeposit deposit = new TimeDeposit(4, "student", 1200.0, 366);

		Double interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualTo(0.0);
	}

	@Test
	void calculateInterest_shouldReturnZero_whenDaysAreAboveMaximum() {
		TimeDeposit deposit = new TimeDeposit(5, "student", 1200.0, 367);

		Double interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualTo(0.0);
	}

	@Test
	void calculateInterest_shouldApplyFormulaForAnyEligibleBalance() {
		TimeDeposit deposit = new TimeDeposit(6, "student", 1000.0, 120);

		Double interest = strategy.calculateInterest(deposit);

		assertThat(interest).isEqualTo(1000.0 * 0.03 / 12.0);
	}
}
