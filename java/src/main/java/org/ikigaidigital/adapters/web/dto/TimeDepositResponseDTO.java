package org.ikigaidigital.adapters.web.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record TimeDepositResponseDTO (
     int id,
     String planType,
     Double balance,
     int days,
     List<WithdrawalsResponseDTO> withdrawals
){}
