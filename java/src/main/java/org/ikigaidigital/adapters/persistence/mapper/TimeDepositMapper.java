package org.ikigaidigital.adapters.persistence.mapper;

import org.ikigaidigital.adapters.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;

import java.util.List;

public class TimeDepositMapper {

    public static TimeDeposit toDomain(TimeDepositEntity entity) {
        return new TimeDeposit(
                entity.getId(),
                entity.getPlanType().toString(),
                entity.getBalance(),
                entity.getDays()
        );
    }

    public static TimeDepositEntity toEntity(TimeDeposit deposit) {
        return TimeDepositEntity.builder()
                .id(deposit.getId())
                .planType(PlanType.valueOf(deposit.getPlanType().toUpperCase()))
                .balance(deposit.getBalance())
                .days(deposit.getDays())
                .build();
    }

    public static List<TimeDeposit> toDomain(List<TimeDepositEntity> entities) {
        return entities.stream().map(TimeDepositMapper::toDomain).toList();
    }

    public static List<TimeDepositEntity> toEntity(List<TimeDeposit> deposits) {
        return deposits.stream().map(TimeDepositMapper::toEntity).toList();
    }
}