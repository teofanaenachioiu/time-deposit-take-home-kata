package org.ikigaidigital.domain.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "withdrawals")
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_deposit_id")
    private Integer timeDepositId;

    private Double amount;

    @Column(name = "transaction_date")
    private LocalDate date;
}
