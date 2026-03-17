package org.ikigaidigital.application.service;

import lombok.AllArgsConstructor;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.domain.service.TimeDepositCalculator;
import org.ikigaidigital.port.in.TimeDepositUpdateBalanceUseCase;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TimeDepositUpdateBalanceService implements TimeDepositUpdateBalanceUseCase {

    private final TimeDepositRepository repository;
    private final TimeDepositCalculator calculator;

    @Override
    public void updateAllTimeDepositsBalance() {
        List<TimeDeposit> timeDepositList = repository.findAll(); // should be improved with pagination/batches

        calculator.updateBalance(timeDepositList);
        repository.save(timeDepositList);
    }
}
