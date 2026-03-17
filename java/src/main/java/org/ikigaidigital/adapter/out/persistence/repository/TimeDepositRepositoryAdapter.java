package org.ikigaidigital.adapter.out.persistence.repository;

import java.util.List;

import org.ikigaidigital.adapter.out.persistence.mapper.TimeDepositMapper;
import org.ikigaidigital.adapter.out.persistence.mapper.TimeDepositViewMapper;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.port.out.TimeDepositQueryRepository;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
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
