package org.ikigaidigital.adapter.out.persistence.mapper;

import java.util.List;

import org.ikigaidigital.adapter.out.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.application.query.model.TimeDepositView;

public class TimeDepositViewMapper {

	public static TimeDepositView toView(TimeDepositEntity entity) {
		return TimeDepositView.builder().id(entity.getId()).planType(entity.getPlanType()).balance(entity.getBalance())
				.days(entity.getDays()).withdrawals(WithdrawalMapper.toView(entity.getWithdrawals())).build();
	}

	public static List<TimeDepositView> toView(List<TimeDepositEntity> entities) {
		return entities.stream().map(TimeDepositViewMapper::toView).toList();
	}
}
