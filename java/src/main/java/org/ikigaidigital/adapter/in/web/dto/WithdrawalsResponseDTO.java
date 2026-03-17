package org.ikigaidigital.adapter.in.web.dto;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record WithdrawalsResponseDTO(Long id, Double amount, LocalDate date) {
}
