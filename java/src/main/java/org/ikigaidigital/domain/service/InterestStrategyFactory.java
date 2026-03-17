package org.ikigaidigital.domain.service;

import org.ikigaidigital.domain.model.PlanType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class InterestStrategyFactory {

    private final Map<PlanType, InterestStrategy> strategies;

    public InterestStrategyFactory(List<InterestStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(InterestStrategy::supports, Function.identity()));
    }

    public InterestStrategy getStrategy(PlanType planType) {
        InterestStrategy strategy = strategies.get(planType);

        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for plan type: " + planType);
        }

        return strategy;
    }
}