package org.ikigaidigital.adapters.persistence.repository;

import org.ikigaidigital.adapters.persistence.mapper.TimeDepositViewMapper;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.springframework.stereotype.Repository;
import org.ikigaidigital.port.out.TimeDepositQueryRepository;

import java.util.List;

@Repository
public class TimeDepositRepositoryAdapter implements TimeDepositQueryRepository {

    private final TimeDepositJpaRepository jpaRepository;

    public TimeDepositRepositoryAdapter(TimeDepositJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<TimeDepositView> findAllWithWithdrawals() {
        return TimeDepositViewMapper.toView(jpaRepository.findAllWithWithdrawals());
    }

}