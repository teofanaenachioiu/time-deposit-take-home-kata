package org.ikigaidigital.domain.service;

import java.math.BigDecimal;

import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;

public interface InterestStrategy {

	PlanType supports();

	BigDecimal calculateInterest(TimeDeposit deposit);

}
