package org.ikigaidigital.port.out;

import java.util.List;

import org.ikigaidigital.domain.model.TimeDeposit;

public interface TimeDepositRepository {

	List<TimeDeposit> findAll();

	void save(List<TimeDeposit> timeDepositList);

}
