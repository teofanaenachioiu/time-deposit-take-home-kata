package org.ikigaidigital.domain.service;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InterestStrategyResolverTest {

    @Test
    void getStrategy_shouldReturnStrategyForBasic() {
        InterestStrategy basic = new StubStrategy(PlanType.BASIC);
        InterestStrategyResolver resolver = new InterestStrategyResolver(List.of(basic));

        InterestStrategy result = resolver.getStrategy(PlanType.BASIC);

        assertThat(result).isSameAs(basic);
    }

    @Test
    void getStrategy_shouldReturnStrategyForEachRegisteredPlanType() {
        InterestStrategy basic = new StubStrategy(PlanType.BASIC);
        InterestStrategy student = new StubStrategy(PlanType.STUDENT);
        InterestStrategy premium = new StubStrategy(PlanType.PREMIUM);
        InterestStrategyResolver resolver = new InterestStrategyResolver(List.of(basic, student, premium));

        assertThat(resolver.getStrategy(PlanType.BASIC)).isSameAs(basic);
        assertThat(resolver.getStrategy(PlanType.STUDENT)).isSameAs(student);
        assertThat(resolver.getStrategy(PlanType.PREMIUM)).isSameAs(premium);
    }

    @Test
    void getStrategy_shouldThrowWhenStrategyDoesNotExist() {
        InterestStrategy basic = new StubStrategy(PlanType.BASIC);
        InterestStrategyResolver resolver = new InterestStrategyResolver(List.of(basic));

        assertThatThrownBy(() -> resolver.getStrategy(PlanType.PREMIUM))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No strategy found for plan type: PREMIUM");
    }

    @Test
    void constructor_shouldThrowWhenTwoStrategiesSupportSamePlanType() {
        InterestStrategy basic1 = new StubStrategy(PlanType.BASIC);
        InterestStrategy basic2 = new StubStrategy(PlanType.BASIC);

        assertThatThrownBy(() -> new InterestStrategyResolver(List.of(basic1, basic2)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Duplicate key");
    }

    private static final class StubStrategy implements InterestStrategy {
        private final PlanType supportedPlanType;

        private StubStrategy(PlanType supportedPlanType) {
            this.supportedPlanType = supportedPlanType;
        }

        @Override
        public PlanType supports() {
            return supportedPlanType;
        }

        @Override
        public Double calculateInterest(TimeDeposit deposit) {
            return 0.0;
        }
    }
}
