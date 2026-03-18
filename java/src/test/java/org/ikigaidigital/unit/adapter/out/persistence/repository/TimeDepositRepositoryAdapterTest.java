package org.ikigaidigital.unit.adapter.out.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.ikigaidigital.adapter.out.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.adapter.out.persistence.entity.WithdrawalEntity;
import org.ikigaidigital.adapter.out.persistence.repository.TimeDepositJpaRepository;
import org.ikigaidigital.adapter.out.persistence.repository.TimeDepositRepositoryAdapter;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimeDepositRepositoryAdapterTest {

	@Mock
	private TimeDepositJpaRepository jpaRepository;

	@Test
	void findAllWithWithdrawals_shouldMapEntitiesToView() {
		TimeDepositRepositoryAdapter adapter = new TimeDepositRepositoryAdapter(jpaRepository);
		List<TimeDepositEntity> entities = List.of(TimeDepositEntity.builder().id(1).planType(PlanType.BASIC)
				.balance(new BigDecimal("100.00")).days(60)
				.withdrawals(List.of(WithdrawalEntity.builder().id(8).amount(new BigDecimal("10.00"))
						.date(LocalDate.of(2026, 1, 10)).build()))
				.build());
		when(jpaRepository.findAllWithWithdrawals()).thenReturn(entities);

		List<TimeDepositView> result = adapter.findAllWithWithdrawals();

		assertThat(result).hasSize(1);
		assertThat(result.get(0).id()).isEqualTo(1);
		assertThat(result.get(0).planType()).isEqualTo(PlanType.BASIC);
		assertThat(result.get(0).withdrawals()).hasSize(1);
		verify(jpaRepository).findAllWithWithdrawals();
		verifyNoMoreInteractions(jpaRepository);
	}

	@Test
	void findAll_shouldMapEntitiesToDomain() {
		TimeDepositRepositoryAdapter adapter = new TimeDepositRepositoryAdapter(jpaRepository);
		List<TimeDepositEntity> entities = List.of(TimeDepositEntity.builder().id(1).planType(PlanType.STUDENT)
				.balance(new BigDecimal("200.00")).days(100).build());
		when(jpaRepository.findAll()).thenReturn(entities);

		List<TimeDeposit> result = adapter.findAll();

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getId()).isEqualTo(1);
		assertThat(result.get(0).getPlanType()).isEqualTo("STUDENT");
		verify(jpaRepository).findAll();
		verifyNoMoreInteractions(jpaRepository);
	}
}
