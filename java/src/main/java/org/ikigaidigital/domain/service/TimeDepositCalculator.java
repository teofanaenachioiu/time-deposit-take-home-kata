package org.ikigaidigital.domain.service;

import lombok.AllArgsConstructor;
import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@AllArgsConstructor
public class TimeDepositCalculator {

    private final InterestStrategyResolver strategyResolver;

    public void updateBalance(List<TimeDeposit> deposits) {
        for (TimeDeposit deposit : deposits) {
            PlanType planType = PlanType.valueOf(deposit.getPlanType().toUpperCase());
            InterestStrategy strategy = strategyResolver.getStrategy(planType);
            Double interest = strategy.calculateInterest(deposit);

            Double updatedBalance = BigDecimal.valueOf(deposit.getBalance() + interest)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            deposit.setBalance(updatedBalance);
        }
    }

}
