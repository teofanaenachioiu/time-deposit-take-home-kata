package org.ikigaidigital.application.query.model;

import java.math.BigDecimal;
import java.util.List;

import org.ikigaidigital.domain.model.PlanType;

import lombok.Builder;

@Builder
public record TimeDepositView(Integer id, PlanType planType, BigDecimal balance, Integer days,
		List<WithdrawalView> withdrawals) {
}
