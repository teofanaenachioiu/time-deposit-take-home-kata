package org.ikigaidigital.adapter.web.mapper;

import org.ikigaidigital.adapter.web.dto.TimeDepositResponseDTO;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.application.query.model.WithdrawalView;
import org.ikigaidigital.domain.model.PlanType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TimeDepositDTOMapperTest {

    @Test
    void toDTO_shouldMapAllFields() {
        TimeDepositView view = TimeDepositView.builder()
                .id(1)
                .planType(PlanType.STUDENT)
                .balance(150.5)
                .days(120)
                .withdrawals(List.of(
                        WithdrawalView.builder().id(11L).amount(20.0).date(LocalDate.of(2026, 2, 1)).build()
                ))
                .build();

        TimeDepositResponseDTO dto = TimeDepositDTOMapper.toDTO(view);

        assertThat(dto.id()).isEqualTo(1);
        assertThat(dto.planType()).isEqualTo(PlanType.STUDENT);
        assertThat(dto.balance()).isEqualTo(150.5);
        assertThat(dto.days()).isEqualTo(120);
        assertThat(dto.withdrawals()).hasSize(1);
        assertThat(dto.withdrawals().get(0).id()).isEqualTo(11L);
    }

    @Test
    void toDTOList_shouldMapEachElement() {
        List<TimeDepositView> views = List.of(
                TimeDepositView.builder().id(1).planType(PlanType.BASIC).balance(10.0).days(10).withdrawals(List.of()).build(),
                TimeDepositView.builder().id(2).planType(PlanType.PREMIUM).balance(20.0).days(20).withdrawals(List.of()).build()
        );

        List<TimeDepositResponseDTO> dtos = TimeDepositDTOMapper.toDTO(views);

        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).id()).isEqualTo(1);
        assertThat(dtos.get(1).id()).isEqualTo(2);
    }
}
