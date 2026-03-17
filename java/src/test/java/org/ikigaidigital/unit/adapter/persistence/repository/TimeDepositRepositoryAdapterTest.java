package org.ikigaidigital.unit.adapter.persistence.repository;

import org.ikigaidigital.adapter.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.adapter.persistence.entity.WithdrawalEntity;
import org.ikigaidigital.adapter.persistence.repository.TimeDepositJpaRepository;
import org.ikigaidigital.adapter.persistence.repository.TimeDepositRepositoryAdapter;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeDepositRepositoryAdapterTest {

    @Mock
    private TimeDepositJpaRepository jpaRepository;

    @Test
    void findAllWithWithdrawals_shouldMapEntitiesToView() {
        TimeDepositRepositoryAdapter adapter = new TimeDepositRepositoryAdapter(jpaRepository);
        List<TimeDepositEntity> entities = List.of(
                TimeDepositEntity.builder()
                        .id(1)
                        .planType(PlanType.BASIC)
                        .balance(100.0)
                        .days(60)
                        .withdrawals(List.of(
                                WithdrawalEntity.builder().id(8L).amount(10.0).date(LocalDate.of(2026, 1, 10)).build()
                        ))
                        .build()
        );
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
        List<TimeDepositEntity> entities = List.of(
                TimeDepositEntity.builder().id(1).planType(PlanType.STUDENT).balance(200.0).days(100).build()
        );
        when(jpaRepository.findAll()).thenReturn(entities);

        List<TimeDeposit> result = adapter.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(0).getPlanType()).isEqualTo("STUDENT");
        verify(jpaRepository).findAll();
        verifyNoMoreInteractions(jpaRepository);
    }

}
