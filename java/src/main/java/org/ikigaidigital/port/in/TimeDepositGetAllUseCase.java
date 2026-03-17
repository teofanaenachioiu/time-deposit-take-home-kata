package org.ikigaidigital.port.in;

import org.ikigaidigital.application.query.model.TimeDepositView;

import java.util.List;

public interface TimeDepositGetAllUseCase {

    List<TimeDepositView> getAll();

}
