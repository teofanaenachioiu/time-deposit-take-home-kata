package org.ikigaidigital.adapter.in.web.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WithdrawalsResponseDTO(
        Long id,
        Double amount,
        LocalDate date
){}
