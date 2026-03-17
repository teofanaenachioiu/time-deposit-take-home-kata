package org.ikigaidigital.port.out;

import org.ikigaidigital.application.query.model.TimeDepositView;

import java.util.List;

public interface TimeDepositQueryRepository {

    List<TimeDepositView> findAllWithWithdrawals();

}
