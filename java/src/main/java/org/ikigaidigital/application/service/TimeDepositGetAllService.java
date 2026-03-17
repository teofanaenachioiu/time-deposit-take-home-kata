package org.ikigaidigital.application.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.port.in.TimeDepositGetAllUseCase;
import org.ikigaidigital.port.out.TimeDepositQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TimeDepositGetAllService implements TimeDepositGetAllUseCase {

    private final TimeDepositQueryRepository queryRepository;

    public List<TimeDepositView> getAll() {
        log.debug("Loading time deposits with withdrawals");
        List<TimeDepositView> deposits = queryRepository.findAllWithWithdrawals();
        log.debug("Loaded {} time deposits with withdrawals", deposits.size());
        return deposits;
    }

}
