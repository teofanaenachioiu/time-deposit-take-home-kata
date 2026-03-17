package org.ikigaidigital.unit.domain.service;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.domain.service.InterestStrategy;
import org.ikigaidigital.domain.service.InterestStrategyResolver;
import org.ikigaidigital.domain.service.TimeDepositCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeDepositCalculatorTest {

    @Mock
    private InterestStrategyResolver strategyResolver;

    @Mock
    private InterestStrategy basicStrategy;

    @Mock
    private InterestStrategy studentStrategy;

    @Mock
    private InterestStrategy premiumStrategy;

    @Test
    void updateBalance_shouldUseBasicStrategy_forLowercasePlanType() {
        TimeDeposit deposit = new TimeDeposit(1, "basic", 100.0, 90);
        TimeDepositCalculator calculator = new TimeDepositCalculator(strategyResolver);

        when(strategyResolver.getStrategy(PlanType.BASIC)).thenReturn(basicStrategy);
        when(basicStrategy.calculateInterest(deposit)).thenReturn(1.11);

        calculator.updateBalance(List.of(deposit));

        assertThat(deposit.getBalance()).isEqualTo(101.11);
        verify(strategyResolver).getStrategy(PlanType.BASIC);
        verify(basicStrategy).calculateInterest(deposit);
        verifyNoMoreInteractions(strategyResolver, basicStrategy, studentStrategy, premiumStrategy);
    }

    @Test
    void updateBalance_shouldUseStudentStrategy_forUppercasePlanType() {
        TimeDeposit deposit = new TimeDeposit(2, "STUDENT", 50.0, 120);
        TimeDepositCalculator calculator = new TimeDepositCalculator(strategyResolver);

        when(strategyResolver.getStrategy(PlanType.STUDENT)).thenReturn(studentStrategy);
        when(studentStrategy.calculateInterest(deposit)).thenReturn(2.0);

        calculator.updateBalance(List.of(deposit));

        assertThat(deposit.getBalance()).isEqualTo(52.0);
        verify(strategyResolver).getStrategy(PlanType.STUDENT);
        verify(studentStrategy).calculateInterest(deposit);
        verifyNoMoreInteractions(strategyResolver, basicStrategy, studentStrategy, premiumStrategy);
    }

    @Test
    void updateBalance_shouldUsePremiumStrategy_forMixedCasePlanType() {
        TimeDeposit deposit = new TimeDeposit(3, "PrEmIuM", 200.0, 180);
        TimeDepositCalculator calculator = new TimeDepositCalculator(strategyResolver);

        when(strategyResolver.getStrategy(PlanType.PREMIUM)).thenReturn(premiumStrategy);
        when(premiumStrategy.calculateInterest(deposit)).thenReturn(3.45);

        calculator.updateBalance(List.of(deposit));

        assertThat(deposit.getBalance()).isEqualTo(203.45);
        verify(strategyResolver).getStrategy(PlanType.PREMIUM);
        verify(premiumStrategy).calculateInterest(deposit);
        verifyNoMoreInteractions(strategyResolver, basicStrategy, studentStrategy, premiumStrategy);
    }

    @Test
    void updateBalance_shouldRoundHalfUpToTwoDecimals() {
        TimeDeposit deposit = new TimeDeposit(4, "basic", 100.0, 60);
        TimeDepositCalculator calculator = new TimeDepositCalculator(strategyResolver);

        when(strategyResolver.getStrategy(PlanType.BASIC)).thenReturn(basicStrategy);
        when(basicStrategy.calculateInterest(deposit)).thenReturn(10.125);

        calculator.updateBalance(List.of(deposit));

        assertThat(deposit.getBalance()).isEqualTo(110.13);
        verify(strategyResolver).getStrategy(PlanType.BASIC);
        verify(basicStrategy).calculateInterest(deposit);
        verifyNoMoreInteractions(strategyResolver, basicStrategy, studentStrategy, premiumStrategy);
    }

    @Test
    void updateBalance_shouldProcessAllDepositsInOrder() {
        TimeDeposit basicDeposit = new TimeDeposit(1, "basic", 100.0, 60);
        TimeDeposit studentDeposit = new TimeDeposit(2, "student", 50.0, 60);
        TimeDeposit premiumDeposit = new TimeDeposit(3, "premium", 80.0, 60);
        TimeDepositCalculator calculator = new TimeDepositCalculator(strategyResolver);

        when(strategyResolver.getStrategy(PlanType.BASIC)).thenReturn(basicStrategy);
        when(strategyResolver.getStrategy(PlanType.STUDENT)).thenReturn(studentStrategy);
        when(strategyResolver.getStrategy(PlanType.PREMIUM)).thenReturn(premiumStrategy);
        when(basicStrategy.calculateInterest(basicDeposit)).thenReturn(1.0);
        when(studentStrategy.calculateInterest(studentDeposit)).thenReturn(2.0);
        when(premiumStrategy.calculateInterest(premiumDeposit)).thenReturn(3.0);

        calculator.updateBalance(List.of(basicDeposit, studentDeposit, premiumDeposit));

        assertThat(basicDeposit.getBalance()).isEqualTo(101.0);
        assertThat(studentDeposit.getBalance()).isEqualTo(52.0);
        assertThat(premiumDeposit.getBalance()).isEqualTo(83.0);

        InOrder inOrder = inOrder(strategyResolver, basicStrategy, studentStrategy, premiumStrategy);
        inOrder.verify(strategyResolver).getStrategy(PlanType.BASIC);
        inOrder.verify(basicStrategy).calculateInterest(basicDeposit);
        inOrder.verify(strategyResolver).getStrategy(PlanType.STUDENT);
        inOrder.verify(studentStrategy).calculateInterest(studentDeposit);
        inOrder.verify(strategyResolver).getStrategy(PlanType.PREMIUM);
        inOrder.verify(premiumStrategy).calculateInterest(premiumDeposit);
        verifyNoMoreInteractions(strategyResolver, basicStrategy, studentStrategy, premiumStrategy);
    }

    @Test
    void updateBalance_shouldDoNothing_forEmptyList() {
        TimeDepositCalculator calculator = new TimeDepositCalculator(strategyResolver);

        calculator.updateBalance(List.of());

        verifyNoInteractions(strategyResolver, basicStrategy, studentStrategy, premiumStrategy);
    }

    @Test
    void updateBalance_shouldThrow_whenPlanTypeIsUnknown() {
        TimeDeposit deposit = new TimeDeposit(5, "vip", 100.0, 30);
        TimeDepositCalculator calculator = new TimeDepositCalculator(strategyResolver);

        assertThatThrownBy(() -> calculator.updateBalance(List.of(deposit)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining("PlanType.VIP");

        verifyNoInteractions(strategyResolver, basicStrategy, studentStrategy, premiumStrategy);
    }

    @Test
    void updateBalance_shouldPropagateResolverException() {
        TimeDeposit deposit = new TimeDeposit(6, "basic", 120.0, 30);
        TimeDepositCalculator calculator = new TimeDepositCalculator(strategyResolver);

        when(strategyResolver.getStrategy(PlanType.BASIC))
                .thenThrow(new IllegalArgumentException("No strategy found for plan type: BASIC"));

        assertThatThrownBy(() -> calculator.updateBalance(List.of(deposit)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy found for plan type: BASIC");

        verify(strategyResolver).getStrategy(PlanType.BASIC);
        verifyNoMoreInteractions(strategyResolver);
        verifyNoInteractions(basicStrategy, studentStrategy, premiumStrategy);
    }
}
