package org.ikigaidigital.unit.adapter.web.controller;

import org.ikigaidigital.adapter.web.controller.TimeDepositController;
import org.ikigaidigital.adapter.web.dto.TimeDepositResponseDTO;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.application.query.model.WithdrawalView;
import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.port.in.TimeDepositGetAllUseCase;
import org.ikigaidigital.port.in.TimeDepositUpdateBalanceUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeDepositControllerTest {

    @Mock
    private TimeDepositGetAllUseCase getAllUseCase;

    @Mock
    private TimeDepositUpdateBalanceUseCase updateBalanceUseCase;

    @Test
    void getAll_shouldReturnOkWithMappedDtoList() {
        TimeDepositController controller = new TimeDepositController(getAllUseCase, updateBalanceUseCase);
        List<TimeDepositView> views = List.of(
                TimeDepositView.builder()
                        .id(1)
                        .planType(PlanType.BASIC)
                        .balance(100.0)
                        .days(60)
                        .withdrawals(List.of(
                                WithdrawalView.builder().id(10L).amount(5.0).date(LocalDate.of(2026, 1, 10)).build()
                        ))
                        .build()
        );
        when(getAllUseCase.getAll()).thenReturn(views);

        ResponseEntity<List<TimeDepositResponseDTO>> response = controller.getAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).id()).isEqualTo(1);
        assertThat(response.getBody().get(0).planType()).isEqualTo(PlanType.BASIC);
        assertThat(response.getBody().get(0).withdrawals()).hasSize(1);
        assertThat(response.getBody().get(0).withdrawals().get(0).id()).isEqualTo(10L);
        verify(getAllUseCase).getAll();
        verifyNoMoreInteractions(getAllUseCase, updateBalanceUseCase);
    }

    @Test
    void updateAllTimeDepositsBalance_shouldReturnNoContent() {
        TimeDepositController controller = new TimeDepositController(getAllUseCase, updateBalanceUseCase);

        ResponseEntity<Void> response = controller.updateAllTimeDepositsBalance();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(updateBalanceUseCase).updateAllTimeDepositsBalance();
        verifyNoMoreInteractions(getAllUseCase, updateBalanceUseCase);
    }
}
