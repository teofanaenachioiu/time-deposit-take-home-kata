package org.ikigaidigital.application.service;

import java.util.List;

import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.domain.service.TimeDepositCalculator;
import org.ikigaidigital.port.in.TimeDepositUpdateBalanceUseCase;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class TimeDepositUpdateBalanceService implements TimeDepositUpdateBalanceUseCase {

	private final TimeDepositRepository repository;
	private final TimeDepositCalculator calculator;

	@Override
	public void updateAllTimeDepositsBalance() {
		log.info("Starting balance update for all time deposits");
		List<TimeDeposit> timeDepositList = repository.findAll(); // should be improved with pagination/batches
		log.debug("Loaded {} time deposits for balance update", timeDepositList.size());

		calculator.updateBalance(timeDepositList);
		repository.save(timeDepositList);
		log.info("Persisted updated balances for {} time deposits", timeDepositList.size());
	}
}
