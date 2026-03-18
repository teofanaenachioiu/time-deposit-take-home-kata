package org.ikigaidigital.application.query.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record WithdrawalView(Integer id, BigDecimal amount, LocalDate date) {
}
