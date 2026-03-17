package org.ikigaidigital.adapter.in.web.dto;

import lombok.Builder;
import org.ikigaidigital.domain.model.PlanType;

import java.util.List;

@Builder
public record TimeDepositResponseDTO (
     Integer id,
     PlanType planType,
     Double balance,
     Integer days,
     List<WithdrawalsResponseDTO> withdrawals
){}
