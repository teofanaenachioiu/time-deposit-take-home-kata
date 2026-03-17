package org.ikigaidigital.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Withdrawal {
    private Long id;
    private Integer timeDepositId;
    private Double amount;
    private LocalDate date;
}
