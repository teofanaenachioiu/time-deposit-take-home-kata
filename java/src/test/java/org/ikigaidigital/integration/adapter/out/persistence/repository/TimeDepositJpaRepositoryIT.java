package org.ikigaidigital.integration.adapter.out.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.ikigaidigital.adapter.out.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.adapter.out.persistence.repository.TimeDepositJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest
@Testcontainers
class TimeDepositJpaRepositoryIT {

	@Container
	static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
			.withDatabaseName("time_deposits").withUsername("postgres").withPassword("postgres");

	@Autowired
	private TimeDepositJpaRepository repository;

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
	void findAllWithWithdrawals_shouldReturnEachDepositOnceWithInitializedWithdrawals() {
		List<TimeDepositEntity> deposits = repository.findAllWithWithdrawals().stream()
				.sorted(Comparator.comparing(TimeDepositEntity::getId)).toList();
		List<Integer> depositIds = deposits.stream().map(TimeDepositEntity::getId).toList();

		assertThat(deposits).hasSize(5);
		assertThat(depositIds).containsExactly(1, 2, 3, 4, 5);
		assertThat(depositIds).doesNotHaveDuplicates();
		assertThat(deposits).extracting(deposit -> deposit.getWithdrawals().size()).containsExactly(0, 2, 3, 1, 0);
	}
}
