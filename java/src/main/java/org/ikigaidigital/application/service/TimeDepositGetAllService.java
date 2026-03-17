package org.ikigaidigital.application.service;

import lombok.AllArgsConstructor;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.port.in.TimeDepositGetAllUseCase;
import org.ikigaidigital.port.out.TimeDepositQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TimeDepositGetAllService implements TimeDepositGetAllUseCase {

    private final TimeDepositQueryRepository queryRepository;

    public List<TimeDepositView> getAll() {
        return queryRepository.findAllWithWithdrawals();
    }

}
