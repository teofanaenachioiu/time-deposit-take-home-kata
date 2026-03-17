package org.ikigaidigital.integration.adapter.persistence.repository;

import org.flywaydb.core.Flyway;
import org.ikigaidigital.adapter.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.adapter.persistence.repository.TimeDepositJpaRepository;
import org.ikigaidigital.adapter.persistence.repository.TimeDepositRepositoryAdapter;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class TimeDepositRepositoryAdapterIT {

    @Container
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("time_deposits")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private TimeDepositRepositoryAdapter adapter;

    @Autowired
    private TimeDepositJpaRepository jpaRepository;

    @Autowired
    private Flyway flyway;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.clean-disabled", () -> false);
    }

    @BeforeEach
    void resetDatabase() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void findAllWithWithdrawals_shouldReturnMappedViews() {
        List<TimeDepositView> deposits = adapter.findAllWithWithdrawals().stream()
                .sorted(Comparator.comparing(TimeDepositView::id))
                .toList();
        List<String> planTypes = deposits.stream().map(deposit -> deposit.planType().name()).toList();

        assertThat(deposits).hasSize(5);
        assertThat(deposits).extracting(TimeDepositView::id).containsExactly(1, 2, 3, 4, 5);
        assertThat(planTypes).containsExactly("BASIC", "STUDENT", "PREMIUM", "STUDENT", "BASIC");
        assertThat(deposits).extracting(deposit -> deposit.withdrawals().size())
                .containsExactly(0, 2, 3, 1, 0);
    }

    @Test
    void findAll_shouldReturnMappedDomainDeposits() {
        List<TimeDeposit> deposits = adapter.findAll().stream()
                .sorted(Comparator.comparing(TimeDeposit::getId))
                .toList();

        assertThat(deposits).hasSize(5);
        assertThat(deposits).extracting(TimeDeposit::getId).containsExactly(1, 2, 3, 4, 5);
        assertThat(deposits).extracting(TimeDeposit::getPlanType)
                .containsExactly("BASIC", "STUDENT", "PREMIUM", "STUDENT", "BASIC");
        assertThat(deposits).extracting(TimeDeposit::getBalance)
                .containsExactly(1000.0, 2000.0, 5000.0, 3000.0, 1500.0);
        assertThat(deposits).extracting(TimeDeposit::getDays)
                .containsExactly(20, 100, 50, 400, 60);
    }

    @Test
    void save_shouldPersistUpdatedBalances() {
        List<TimeDeposit> updatedDeposits = List.of(
                new TimeDeposit(1, "BASIC", 1111.11, 20),
                new TimeDeposit(2, "STUDENT", 2222.22, 100),
                new TimeDeposit(3, "PREMIUM", 3333.33, 50),
                new TimeDeposit(4, "STUDENT", 4444.44, 400),
                new TimeDeposit(5, "BASIC", 5555.55, 60)
        );

        adapter.save(updatedDeposits);

        List<TimeDepositEntity> persistedDeposits = jpaRepository.findAll().stream()
                .sorted(Comparator.comparing(TimeDepositEntity::getId))
                .toList();
        List<String> persistedPlanTypes = persistedDeposits.stream()
                .map(deposit -> deposit.getPlanType().name())
                .toList();

        assertThat(persistedDeposits).hasSize(5);
        assertThat(persistedDeposits).extracting(TimeDepositEntity::getBalance)
                .containsExactly(1111.11, 2222.22, 3333.33, 4444.44, 5555.55);
        assertThat(persistedPlanTypes).containsExactly("BASIC", "STUDENT", "PREMIUM", "STUDENT", "BASIC");
        assertThat(jpaRepository.findAllWithWithdrawals()).extracting(deposit -> deposit.getWithdrawals().size())
                .containsExactlyInAnyOrder(0, 0, 1, 2, 3);
    }
}
