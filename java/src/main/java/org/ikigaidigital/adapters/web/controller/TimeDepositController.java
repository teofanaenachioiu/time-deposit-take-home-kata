package org.ikigaidigital.adapters.web.controller;

import lombok.RequiredArgsConstructor;
import org.ikigaidigital.adapters.web.dto.TimeDepositResponseDTO;
import org.ikigaidigital.adapters.web.mapper.TimeDepositDTOMapper;
import org.ikigaidigital.port.in.TimeDepositGetAllUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/time-deposits")
public class TimeDepositController {

    private final TimeDepositGetAllUseCase getAllUseCase;

    @GetMapping
    public List<TimeDepositResponseDTO> getAll() {
        return TimeDepositDTOMapper.toDTO(getAllUseCase.getAll());
    }
}
