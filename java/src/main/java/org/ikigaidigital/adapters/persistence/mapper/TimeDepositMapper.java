package org.ikigaidigital.adapters.persistence.mapper;

import org.ikigaidigital.adapters.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.domain.model.TimeDeposit;

public class TimeDepositMapper {

    public static TimeDeposit toDomain(TimeDepositEntity entity) {
        return new TimeDeposit(
                entity.getId(),
                entity.getPlanType(),
                entity.getBalance(),
                entity.getDays()
        );
    }

    public static TimeDepositEntity toEntity(TimeDeposit deposit) {
        return TimeDepositEntity.builder()
                .id(deposit.getId())
                .planType(deposit.getPlanType())
                .balance(deposit.getBalance())
                .days(deposit.getDays())
                .build();
    }
}