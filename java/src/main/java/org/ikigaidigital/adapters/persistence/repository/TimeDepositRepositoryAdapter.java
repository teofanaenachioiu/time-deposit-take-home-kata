package org.ikigaidigital.adapters.persistence.repository;

import org.ikigaidigital.adapters.persistence.mapper.TimeDepositMapper;
import org.ikigaidigital.adapters.persistence.mapper.TimeDepositViewMapper;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.springframework.stereotype.Repository;
import org.ikigaidigital.port.out.TimeDepositQueryRepository;

import java.util.List;

@Repository
public class TimeDepositRepositoryAdapter implements TimeDepositQueryRepository, TimeDepositRepository {

    private final TimeDepositJpaRepository jpaRepository;

    public TimeDepositRepositoryAdapter(TimeDepositJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<TimeDepositView> findAllWithWithdrawals() {
        return TimeDepositViewMapper.toView(jpaRepository.findAllWithWithdrawals());
    }

    @Override
    public List<TimeDeposit> findAll() {
        return TimeDepositMapper.toDomain(jpaRepository.findAll());
    }

    @Override
    public void save(List<TimeDeposit> timeDepositList) {
        jpaRepository.saveAll(TimeDepositMapper.toEntity(timeDepositList));
    }
}