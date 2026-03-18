package org.ikigaidigital.unit.adapter.in.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.ikigaidigital.adapter.in.web.dto.TimeDepositResponseDTO;
import org.ikigaidigital.adapter.in.web.mapper.TimeDepositDTOMapper;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.application.query.model.WithdrawalView;
import org.ikigaidigital.domain.model.PlanType;
import org.junit.jupiter.api.Test;

class TimeDepositDTOMapperTest {

	@Test
	void toDTO_shouldMapAllFields() {
		TimeDepositView view = TimeDepositView.builder().id(1).planType(PlanType.STUDENT)
				.balance(new BigDecimal("150.50")).days(120)
				.withdrawals(List.of(WithdrawalView.builder().id(11).amount(new BigDecimal("20.00"))
						.date(LocalDate.of(2026, 2, 1)).build()))
				.build();

		TimeDepositResponseDTO dto = TimeDepositDTOMapper.toDTO(view);

		assertThat(dto.id()).isEqualTo(1);
		assertThat(dto.planType()).isEqualTo(PlanType.STUDENT);
		assertThat(dto.balance()).isEqualByComparingTo("150.50");
		assertThat(dto.days()).isEqualTo(120);
		assertThat(dto.withdrawals()).hasSize(1);
		assertThat(dto.withdrawals().get(0).id()).isEqualTo(11);
	}

	@Test
	void toDTOList_shouldMapEachElement() {
		List<TimeDepositView> views = List.of(
				TimeDepositView.builder().id(1).planType(PlanType.BASIC).balance(new BigDecimal("10.00")).days(10)
						.withdrawals(List.of()).build(),
				TimeDepositView.builder().id(2).planType(PlanType.PREMIUM).balance(new BigDecimal("20.00")).days(20)
						.withdrawals(List.of()).build());

		List<TimeDepositResponseDTO> dtos = TimeDepositDTOMapper.toDTO(views);

		assertThat(dtos).hasSize(2);
		assertThat(dtos.get(0).id()).isEqualTo(1);
		assertThat(dtos.get(1).id()).isEqualTo(2);
	}
}
