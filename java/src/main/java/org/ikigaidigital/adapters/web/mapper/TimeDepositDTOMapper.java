package org.ikigaidigital.adapters.web.mapper;

import org.ikigaidigital.adapters.web.dto.TimeDepositResponseDTO;
import org.ikigaidigital.application.query.model.TimeDepositView;

import java.util.List;

public class TimeDepositDTOMapper {

    public static TimeDepositResponseDTO toDTO(TimeDepositView timeDepositView) {
        return TimeDepositResponseDTO.builder()
                .id(timeDepositView.id())
                .balance(timeDepositView.balance())
                .planType(timeDepositView.planType())
                .days(timeDepositView.days())
                .withdrawals(WithdrawalsDTOMapper.toDTO(timeDepositView.withdrawals()))
                .build();
    }

    public static List<TimeDepositResponseDTO> toDTO(List<TimeDepositView> timeDepositViewList) {
        return timeDepositViewList.stream().map(TimeDepositDTOMapper::toDTO).toList();
    }

}
