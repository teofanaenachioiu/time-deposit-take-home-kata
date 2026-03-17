package org.ikigaidigital.application.query.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WithdrawalView(
        Long id,
        Double amount,
        LocalDate date
) {}
