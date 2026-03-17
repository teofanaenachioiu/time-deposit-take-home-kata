package org.ikigaidigital.adapter.out.persistence.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "withdrawals")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double amount;

	@Column(name = "transaction_date")
	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "time_deposit_id", nullable = false)
	private TimeDepositEntity timeDeposit;
}
