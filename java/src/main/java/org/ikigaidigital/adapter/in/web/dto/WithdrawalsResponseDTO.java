package org.ikigaidigital.adapter.in.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record WithdrawalsResponseDTO(Integer id, BigDecimal amount, LocalDate date) {
}
