package org.ikigaidigital.adapter.in.web.dto;

import java.math.BigDecimal;
import java.util.List;

import org.ikigaidigital.domain.model.PlanType;

import lombok.Builder;

@Builder
public record TimeDepositResponseDTO(Integer id, PlanType planType, BigDecimal balance, Integer days,
		List<WithdrawalsResponseDTO> withdrawals) {
}
