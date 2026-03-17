package org.ikigaidigital.domain.service;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PremiumInterestStrategyTest {

    private final PremiumInterestStrategy strategy = new PremiumInterestStrategy();

    @Test
    void supports_shouldReturnPremiumPlanType() {
        assertThat(strategy.supports()).isEqualTo(PlanType.PREMIUM);
    }

    @Test
    void calculateInterest_shouldReturnZero_whenDaysAreBelowMinimum() {
        TimeDeposit deposit = new TimeDeposit(1, "premium", 1200.0, 44);

        Double interest = strategy.calculateInterest(deposit);

        assertThat(interest).isEqualTo(0.0);
    }

    @Test
    void calculateInterest_shouldReturnZero_whenDaysAreExactlyMinimum() {
        TimeDeposit deposit = new TimeDeposit(2, "premium", 1200.0, 45);

        Double interest = strategy.calculateInterest(deposit);

        assertThat(interest).isEqualTo(0.0);
    }

    @Test
    void calculateInterest_shouldCalculateInterest_whenDaysAreAboveMinimum() {
        TimeDeposit deposit = new TimeDeposit(3, "premium", 1200.0, 46);

        Double interest = strategy.calculateInterest(deposit);

        assertThat(interest).isEqualTo(5.0);
    }

    @Test
    void calculateInterest_shouldApplyFormulaForAnyBalanceAboveMinimumDays() {
        TimeDeposit deposit = new TimeDeposit(4, "premium", 1000.0, 100);

        Double interest = strategy.calculateInterest(deposit);

        assertThat(interest).isEqualTo(1000.0 * 0.05 / 12.0);
    }
}
