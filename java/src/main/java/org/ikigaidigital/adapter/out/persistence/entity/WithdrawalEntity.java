package org.ikigaidigital.adapter.out.persistence.entity;

import java.math.BigDecimal;
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
	private Integer id;

	private BigDecimal amount;

	@Column(name = "transaction_date")
	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "time_deposit_id", nullable = false)
	private TimeDepositEntity timeDeposit;
}
