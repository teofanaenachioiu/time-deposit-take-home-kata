package org.ikigaidigital.application.query.model;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record WithdrawalView(Long id, Double amount, LocalDate date) {
}
