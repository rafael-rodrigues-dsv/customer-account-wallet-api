package dev.challenge.api.adapter.entrypoint.controller;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.transfer.CancelTransferDto;
import dev.challenge.api.adapter.entrypoint.dto.transfer.PerformTransferDto;
import dev.challenge.api.adapter.entrypoint.dto.transfer.TransferDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/transfers")
@Tag(name = "Transfers", description = "Operations related to Transfers")
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
@io.swagger.v3.oas.annotations.media.Schema(
    name = "application/json",
    implementation = TransferController.class
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransferController {

  private final ServiceCommand<PerformTransferDto, TransferDto> createTransferCommand;
  private final ServiceCommand<CancelTransferDto, TransferDto> cancelTransferCommand;
  private final ServiceCommand<Long, TransferDto> findByIdCommand;

  @Operation(summary = "Perform a Transfer")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<TransferDto> performTransfer(
      @RequestBody @Parameter(description = "Transfer data to be created")
      PerformTransferDto performTransferDto) {

    TransferDto createdTransfer = createTransferCommand.execute(performTransferDto);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(createdTransfer.getId()).toUri();

    return ResponseEntity.created(location).body(createdTransfer);
  }

  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Cancel a Transfer")
  @DeleteMapping("/{id}")
  public ResponseEntity<TransferDto> update(
      @PathVariable @Parameter(description = "ID of the Transfer") Long id) {

    CancelTransferDto cancelTransferDto = CancelTransferDto.builder()
        .id(id)
        .build();

    TransferDto cancelledTransfer = cancelTransferCommand.execute(cancelTransferDto);

    return cancelledTransfer != null ? ResponseEntity.ok(cancelledTransfer)
        : ResponseEntity.notFound().build();
  }

  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Get a Transfer by ID")
  @GetMapping("/{id}")
  public ResponseEntity<TransferDto> findById(
      @PathVariable @Parameter(description = "ID of the Transfer") Long id) {

    TransferDto transfer = findByIdCommand.execute(id);

    return transfer != null ? ResponseEntity.ok(transfer)
        : ResponseEntity.notFound().build();
  }
}
