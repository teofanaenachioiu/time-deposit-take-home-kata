package org.ikigaidigital.application.query.model;

import lombok.Builder;
import org.ikigaidigital.domain.model.PlanType;

import java.util.List;

@Builder
public record TimeDepositView(
        Integer id,
        PlanType planType,
        Double balance,
        Integer days,
        List<WithdrawalView> withdrawals
) {}