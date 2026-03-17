package org.ikigaidigital.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ikigaidigital.domain.model.PlanType;

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
    @Enumerated(EnumType.STRING)
    private PlanType planType;

    private Double balance;

    private Integer days;

    @OneToMany(mappedBy = "timeDeposit", fetch = FetchType.LAZY)
    private List<WithdrawalEntity> withdrawals;

}
