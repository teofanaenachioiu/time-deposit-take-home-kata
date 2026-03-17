package org.ikigaidigital.adapter.out.persistence.mapper;

import org.ikigaidigital.adapter.out.persistence.entity.WithdrawalEntity;
import org.ikigaidigital.application.query.model.WithdrawalView;

import java.util.List;

public class WithdrawalMapper {

    public static WithdrawalView toView(WithdrawalEntity entity) {
        return WithdrawalView.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .amount(entity.getAmount())
                .build();
    }

    public static List<WithdrawalView> toView(List<WithdrawalEntity> entities) {
        return entities.stream().map(WithdrawalMapper::toView).toList();
    }
}
