package org.ikigaidigital.application.query.model;

import lombok.Builder;

import java.util.List;

@Builder
public record TimeDepositView(
        int id,
        String planType,
        Double balance,
        int days,
        List<WithdrawalView> withdrawals
) {}