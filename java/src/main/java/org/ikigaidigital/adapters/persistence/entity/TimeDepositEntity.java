package org.ikigaidigital.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "time_deposits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimeDepositEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "plan_type")
    private String planType;

    private Double balance;

    private int days;

    @OneToMany(mappedBy = "timeDeposit", fetch = FetchType.LAZY)
    private List<WithdrawalEntity> withdrawals;

}
