package org.ikigaidigital.domain.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.ikigaidigital.domain.model.PlanType;
import org.springframework.stereotype.Component;

@Component
public class InterestStrategyResolver {

	private final Map<PlanType, InterestStrategy> strategies;

	public InterestStrategyResolver(List<InterestStrategy> strategies) {
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
