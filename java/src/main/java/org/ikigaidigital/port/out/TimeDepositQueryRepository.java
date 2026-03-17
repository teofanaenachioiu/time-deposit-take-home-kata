package org.ikigaidigital.port.out;

import java.util.List;

import org.ikigaidigital.application.query.model.TimeDepositView;

public interface TimeDepositQueryRepository {

	List<TimeDepositView> findAllWithWithdrawals();

}
