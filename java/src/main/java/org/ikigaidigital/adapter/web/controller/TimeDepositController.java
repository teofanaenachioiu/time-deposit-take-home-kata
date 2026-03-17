package org.ikigaidigital.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ikigaidigital.adapter.web.dto.TimeDepositResponseDTO;
import org.ikigaidigital.adapter.web.mapper.TimeDepositDTOMapper;
import org.ikigaidigital.port.in.TimeDepositGetAllUseCase;
import org.ikigaidigital.port.in.TimeDepositUpdateBalanceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("${api.base-path}/time-deposits")
@Tag(name = "Time Deposits", description = "Operations for time deposit resources")
public class TimeDepositController {

    private final TimeDepositGetAllUseCase getAllUseCase;

    private final TimeDepositUpdateBalanceUseCase updateBalanceUseCase;

    @Operation(
            summary = "Get all time deposits",
            description = "Returns all time deposits together with their withdrawals."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Time deposits retrieved successfully",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = TimeDepositResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<TimeDepositResponseDTO>> getAll() {
        log.info("Received request to fetch all time deposits");
        List<TimeDepositResponseDTO> timeDepositResponseDTOS = TimeDepositDTOMapper.toDTO(getAllUseCase.getAll());
        log.debug("Returning {} time deposits", timeDepositResponseDTOS.size());
        return ResponseEntity.ok(timeDepositResponseDTOS);
    }

    @Operation(
            summary = "Update balances for all time deposits",
            description = "Calculates and updates the balance for all time deposits."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Balances updated successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content
            )
    })
    @PostMapping("/update-balances")
    public ResponseEntity<Void> updateAllTimeDepositsBalance() {
        log.info("Received request to update all time deposit balances");
        updateBalanceUseCase.updateAllTimeDepositsBalance();
        log.info("Finished updating all time deposit balances");
        return ResponseEntity.noContent().build();
    }
}
