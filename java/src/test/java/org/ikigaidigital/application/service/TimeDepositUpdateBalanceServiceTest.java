package org.ikigaidigital.application.service;

import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.domain.service.TimeDepositCalculator;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeDepositUpdateBalanceServiceTest {

    @Mock
    private TimeDepositRepository repository;

    @Mock
    private TimeDepositCalculator calculator;

    @Test
    void updateAllTimeDepositsBalance_shouldLoadUpdateAndSave_inOrder() {
        TimeDepositUpdateBalanceService service = new TimeDepositUpdateBalanceService(repository, calculator);
        List<TimeDeposit> deposits = List.of(
                new TimeDeposit(1, "basic", 100.0, 60),
                new TimeDeposit(2, "student", 200.0, 120)
        );
        when(repository.findAll()).thenReturn(deposits);

        service.updateAllTimeDepositsBalance();

        InOrder inOrder = inOrder(repository, calculator);
        inOrder.verify(repository).findAll();
        inOrder.verify(calculator).updateBalance(deposits);
        inOrder.verify(repository).save(deposits);
        verifyNoMoreInteractions(repository, calculator);
    }

    @Test
    void updateAllTimeDepositsBalance_shouldStillCallCalculatorAndSave_whenListIsEmpty() {
        TimeDepositUpdateBalanceService service = new TimeDepositUpdateBalanceService(repository, calculator);
        List<TimeDeposit> deposits = List.of();
        when(repository.findAll()).thenReturn(deposits);

        service.updateAllTimeDepositsBalance();

        verify(repository).findAll();
        verify(calculator).updateBalance(deposits);
        verify(repository).save(deposits);
        verifyNoMoreInteractions(repository, calculator);
    }
}
