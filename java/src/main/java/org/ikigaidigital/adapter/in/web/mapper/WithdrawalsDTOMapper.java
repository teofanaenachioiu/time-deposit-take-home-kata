package org.ikigaidigital.adapter.in.web.mapper;

import java.util.List;

import org.ikigaidigital.adapter.in.web.dto.WithdrawalsResponseDTO;
import org.ikigaidigital.application.query.model.WithdrawalView;

public class WithdrawalsDTOMapper {

	public static WithdrawalsResponseDTO toDTO(WithdrawalView withdrawalView) {
		return WithdrawalsResponseDTO.builder().id(withdrawalView.id()).amount(withdrawalView.amount())
				.date(withdrawalView.date()).build();
	}

	public static List<WithdrawalsResponseDTO> toDTO(List<WithdrawalView> withdrawalViewList) {
		return withdrawalViewList.stream().map(WithdrawalsDTOMapper::toDTO).toList();
	}

}
