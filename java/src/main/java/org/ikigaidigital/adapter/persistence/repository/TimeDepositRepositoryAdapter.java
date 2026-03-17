package org.ikigaidigital.adapter.persistence.repository;

import lombok.extern.slf4j.Slf4j;
import org.ikigaidigital.adapter.persistence.mapper.TimeDepositMapper;
import org.ikigaidigital.adapter.persistence.mapper.TimeDepositViewMapper;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.springframework.stereotype.Repository;
import org.ikigaidigital.port.out.TimeDepositQueryRepository;

import java.util.List;

@Repository
@Slf4j
public class TimeDepositRepositoryAdapter implements TimeDepositQueryRepository, TimeDepositRepository {

    private final TimeDepositJpaRepository jpaRepository;

    public TimeDepositRepositoryAdapter(TimeDepositJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<TimeDepositView> findAllWithWithdrawals() {
        log.debug("Fetching time deposits with withdrawals from database");
        return TimeDepositViewMapper.toView(jpaRepository.findAllWithWithdrawals());
    }

    @Override
    public List<TimeDeposit> findAll() {
        log.debug("Fetching all time deposits from database");
        return TimeDepositMapper.toDomain(jpaRepository.findAll());
    }

    @Override
    public void save(List<TimeDeposit> timeDepositList) {
        log.debug("Saving {} time deposits", timeDepositList.size());
        jpaRepository.saveAll(TimeDepositMapper.toEntity(timeDepositList));
    }
}
