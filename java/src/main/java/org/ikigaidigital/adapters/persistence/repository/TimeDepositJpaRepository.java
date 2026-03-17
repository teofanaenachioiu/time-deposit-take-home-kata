package org.ikigaidigital.adapters.persistence.repository;

import org.ikigaidigital.adapters.persistence.entity.TimeDepositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeDepositJpaRepository extends JpaRepository<TimeDepositEntity, Integer> {

    @Query("""
        SELECT DISTINCT td
        FROM TimeDepositEntity td
        LEFT JOIN FETCH td.withdrawals
    """)
    List<TimeDepositEntity> findAllWithWithdrawals();
}