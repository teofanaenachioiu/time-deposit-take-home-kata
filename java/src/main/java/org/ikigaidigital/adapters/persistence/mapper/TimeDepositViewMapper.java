package org.ikigaidigital.adapters.persistence.mapper;

import org.ikigaidigital.adapters.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.application.query.model.TimeDepositView;

import java.util.List;

public class TimeDepositViewMapper {

    public static TimeDepositView toView(TimeDepositEntity entity) {
        return TimeDepositView.builder()
                .id(entity.getId())
                .planType(entity.getPlanType())
                .balance(entity.getBalance())
                .days(entity.getDays())
                .withdrawals(WithdrawalMapper.toView(entity.getWithdrawals()))
                .build();
    }

    public static List<TimeDepositView> toView(List<TimeDepositEntity> entities) {
        return entities.stream().map(TimeDepositViewMapper::toView).toList();
    }
}