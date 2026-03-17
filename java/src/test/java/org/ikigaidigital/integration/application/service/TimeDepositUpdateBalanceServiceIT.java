package org.ikigaidigital.integration.application.service;

import org.flywaydb.core.Flyway;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.port.in.TimeDepositUpdateBalanceUseCase;
import org.ikigaidigital.port.out.TimeDepositRepository;
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
class TimeDepositUpdateBalanceServiceIT {

    @Container
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("time_deposits")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private TimeDepositUpdateBalanceUseCase updateBalanceUseCase;

    @Autowired
    private TimeDepositRepository repository;

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
    void updateAllTimeDepositsBalance_shouldRecalculateAndPersistBalances() {
        updateBalanceUseCase.updateAllTimeDepositsBalance();

        List<TimeDeposit> persistedDeposits = repository.findAll().stream()
                .sorted(Comparator.comparing(TimeDeposit::getId))
                .toList();

        assertThat(persistedDeposits).hasSize(5);
        assertThat(persistedDeposits).extracting(TimeDeposit::getId)
                .containsExactly(1, 2, 3, 4, 5);
        assertThat(persistedDeposits).extracting(TimeDeposit::getBalance)
                .containsExactly(1000.0, 2005.0, 5020.83, 3000.0, 1501.25);
    }
}
