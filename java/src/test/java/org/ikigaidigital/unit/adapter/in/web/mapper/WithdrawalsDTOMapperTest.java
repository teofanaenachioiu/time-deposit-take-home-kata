package org.ikigaidigital.unit.adapter.in.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.ikigaidigital.adapter.in.web.dto.WithdrawalsResponseDTO;
import org.ikigaidigital.adapter.in.web.mapper.WithdrawalsDTOMapper;
import org.ikigaidigital.application.query.model.WithdrawalView;
import org.junit.jupiter.api.Test;

class WithdrawalsDTOMapperTest {

	@Test
	void toDTO_shouldMapAllFields() {
		WithdrawalView view = WithdrawalView.builder().id(7L).amount(55.75).date(LocalDate.of(2026, 1, 5)).build();

		WithdrawalsResponseDTO dto = WithdrawalsDTOMapper.toDTO(view);

		assertThat(dto.id()).isEqualTo(7L);
		assertThat(dto.amount()).isEqualTo(55.75);
		assertThat(dto.date()).isEqualTo(LocalDate.of(2026, 1, 5));
	}

	@Test
	void toDTOList_shouldMapEachElement() {
		List<WithdrawalView> views = List.of(
				WithdrawalView.builder().id(1L).amount(10.0).date(LocalDate.of(2026, 1, 1)).build(),
				WithdrawalView.builder().id(2L).amount(20.0).date(LocalDate.of(2026, 1, 2)).build());

		List<WithdrawalsResponseDTO> dtos = WithdrawalsDTOMapper.toDTO(views);

		assertThat(dtos).hasSize(2);
		assertThat(dtos.get(0).id()).isEqualTo(1L);
		assertThat(dtos.get(1).id()).isEqualTo(2L);
	}
}
