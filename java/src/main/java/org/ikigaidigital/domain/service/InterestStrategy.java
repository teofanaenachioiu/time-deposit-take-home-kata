package org.ikigaidigital.domain.service;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;

public interface InterestStrategy {

    PlanType supports();

    Double calculateInterest(TimeDeposit deposit);

}
