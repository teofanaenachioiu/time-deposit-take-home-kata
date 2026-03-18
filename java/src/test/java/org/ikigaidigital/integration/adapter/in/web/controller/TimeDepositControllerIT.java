package org.ikigaidigital.integration.adapter.in.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.ikigaidigital.adapter.in.web.dto.TimeDepositResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TimeDepositControllerIT {

    private final MockMvc mockMvc;
    private final Flyway flyway;
    private final ObjectMapper objectMapper;

	@Container
	static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
			.withDatabaseName("time_deposits").withUsername("postgres").withPassword("postgres");

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
	void getAll_shouldReturnAllTimeDepositsWithWithdrawals() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/v1/time-deposits")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json")).andReturn();

		List<TimeDepositResponseDTO> deposits = readAndSortResponse(result);

		assertThat(deposits).hasSize(5);
		assertThat(deposits.get(0).id()).isEqualTo(1);
		assertThat(deposits.get(0).planType().name()).isEqualTo("BASIC");
		assertThat(deposits.get(0).balance()).isEqualTo(1000.0);
		assertThat(deposits.get(0).withdrawals()).hasSize(0);
		assertThat(deposits.get(1).id()).isEqualTo(2);
		assertThat(deposits.get(1).planType().name()).isEqualTo("STUDENT");
		assertThat(deposits.get(1).withdrawals()).hasSize(2);
		assertThat(deposits.get(2).id()).isEqualTo(3);
		assertThat(deposits.get(2).planType().name()).isEqualTo("PREMIUM");
		assertThat(deposits.get(2).withdrawals()).hasSize(3);
		assertThat(deposits.get(3).id()).isEqualTo(4);
		assertThat(deposits.get(3).withdrawals()).hasSize(1);
		assertThat(deposits.get(4).id()).isEqualTo(5);
		assertThat(deposits.get(4).withdrawals()).hasSize(0);
	}

	@Test
	void updateAllTimeDepositsBalance_shouldReturnNoContentAndPersistUpdatedBalances() throws Exception {
		mockMvc.perform(post("/api/v1/time-deposits/update-balances")).andExpect(status().isNoContent());

		MvcResult result = mockMvc.perform(get("/api/v1/time-deposits")).andExpect(status().isOk()).andReturn();

		List<TimeDepositResponseDTO> deposits = readAndSortResponse(result);

		assertThat(deposits).hasSize(5);
		assertThat(deposits.get(0).balance()).isEqualTo(1000.0);
		assertThat(deposits.get(1).balance()).isEqualTo(2005.0);
		assertThat(deposits.get(2).balance()).isEqualTo(5020.83);
		assertThat(deposits.get(3).balance()).isEqualTo(3000.0);
		assertThat(deposits.get(4).balance()).isEqualTo(1501.25);
	}

	private List<TimeDepositResponseDTO> readAndSortResponse(MvcResult result) {
		TimeDepositResponseDTO[] response = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
				TimeDepositResponseDTO[].class);

		return Arrays.stream(response).sorted(Comparator.comparing(TimeDepositResponseDTO::id)).toList();
	}
}
