package org.ikigaidigital.adapters.web.mapper;

import org.ikigaidigital.adapters.web.dto.WithdrawalsResponseDTO;
import org.ikigaidigital.application.query.model.WithdrawalView;

import java.util.List;

public class WithdrawalsDTOMapper {

    public static WithdrawalsResponseDTO toDTO(WithdrawalView withdrawalView) {
        return WithdrawalsResponseDTO.builder()
                .id(withdrawalView.id())
                .amount(withdrawalView.amount())
                .date(withdrawalView.date())
                .build();
    }

    public static List<WithdrawalsResponseDTO> toDTO(List<WithdrawalView> withdrawalViewList) {
        return withdrawalViewList.stream().map(WithdrawalsDTOMapper::toDTO).toList();
    }

}
