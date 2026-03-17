package org.ikigaidigital.unit.domain.service;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.domain.service.BasicInterestStrategy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasicInterestStrategyTest {

    private final BasicInterestStrategy strategy = new BasicInterestStrategy();

    @Test
    void supports_shouldReturnBasicPlanType() {
        assertThat(strategy.supports()).isEqualTo(PlanType.BASIC);
    }

    @Test
    void calculateInterest_shouldReturnZero_whenDaysAreBelowMinimum() {
        TimeDeposit deposit = new TimeDeposit(1, "basic", 1200.0, 29);

        Double interest = strategy.calculateInterest(deposit);

        assertThat(interest).isEqualTo(0.0);
    }

    @Test
    void calculateInterest_shouldReturnZero_whenDaysAreExactlyMinimum() {
        TimeDeposit deposit = new TimeDeposit(2, "basic", 1200.0, 30);

        Double interest = strategy.calculateInterest(deposit);

        assertThat(interest).isEqualTo(0.0);
    }

    @Test
    void calculateInterest_shouldCalculateInterest_whenDaysAreAboveMinimum() {
        TimeDeposit deposit = new TimeDeposit(3, "basic", 1200.0, 31);

        Double interest = strategy.calculateInterest(deposit);

        assertThat(interest).isEqualTo(1.0);
    }

    @Test
    void calculateInterest_shouldApplyFormulaForAnyBalanceAboveMinimumDays() {
        TimeDeposit deposit = new TimeDeposit(4, "basic", 1000.0, 365);

        Double interest = strategy.calculateInterest(deposit);

        assertThat(interest).isEqualTo(1000.0 * 0.01 / 12.0);
    }
}
