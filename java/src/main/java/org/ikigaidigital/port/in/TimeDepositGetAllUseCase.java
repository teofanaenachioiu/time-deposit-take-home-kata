package org.ikigaidigital.port.in;

import java.util.List;

import org.ikigaidigital.application.query.model.TimeDepositView;

public interface TimeDepositGetAllUseCase {

	List<TimeDepositView> getAll();

}
