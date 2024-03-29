package dev.challenge.api.adapter.entrypoint.controller;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CreateCustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountBalanceDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountStatusDto;
import dev.challenge.api.adapter.entrypoint.dto.filter.FindByIdAndCustomerIdFilterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customers/{customerId}/customer-accounts", produces = "application/json")
@Tag(name = "Customer Accounts", description = "Operations related to Customer Accounts")
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerAccountController {

  private final ServiceCommand<CreateCustomerAccountDto, CustomerAccountDto> createCustomerAccountCommand;
  private final ServiceCommand<UpdateCustomerAccountDto, CustomerAccountDto> updateCustomerAccountCommand;
  private final ServiceCommand<UpdateCustomerAccountBalanceDto, CustomerAccountDto> updateCustomerAccountBalanceCommand;
  private final ServiceCommand<UpdateCustomerAccountStatusDto, CustomerAccountDto> updateCustomerAccountStatusCommand;
  private final ServiceCommand<FindByIdAndCustomerIdFilterDto, CustomerAccountDto> findByIdCustomerAccountCommand;
  private final ServiceCommand<Long, List<CustomerAccountDto>> findAllCustomerAccountCommand;

  @Operation(summary = "Create an Account")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CustomerAccountDto> create(
      @PathVariable @Parameter(description = "ID of the customer") Long customerId,
      @RequestBody @Valid CreateCustomerAccountDto createAccountDto) {

    createAccountDto.setCustomerId(customerId);
    CustomerAccountDto createdAccount = createCustomerAccountCommand.execute(createAccountDto);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(createdAccount.getId()).toUri();

    return ResponseEntity.created(location).body(createdAccount);
  }

  @ApiResponse(responseCode = "200", description = "OK", content = @Content)
  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Update a Customer Account by ID")
  @PatchMapping("/{id}")
  public ResponseEntity<CustomerAccountDto> update(
      @PathVariable @Parameter(description = "ID of the Customer Account") Long id,
      @PathVariable @Parameter(description = "ID of the Customer") Long customerId,
      @RequestBody @Valid UpdateCustomerAccountDto updateCustomerAccountDto) {
    updateCustomerAccountDto.setId(id);
    updateCustomerAccountDto.setCustomerId(customerId);
    return ResponseEntity.ok(updateCustomerAccountCommand.execute(updateCustomerAccountDto));
  }

  @ApiResponse(responseCode = "200", description = "OK", content = @Content)
  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Update a Customer Account Balance by ID")
  @PatchMapping("/{id}/update-balance")
  public ResponseEntity<CustomerAccountDto> updateBalance(
      @PathVariable @Parameter(description = "ID of the Customer Account") Long id,
      @PathVariable @Parameter(description = "ID of the Customer") Long customerId,
      @RequestBody @Valid UpdateCustomerAccountBalanceDto updateCustomerAccountDto) {
    updateCustomerAccountDto.setId(id);
    updateCustomerAccountDto.setCustomerId(customerId);
    return ResponseEntity.ok(updateCustomerAccountBalanceCommand.execute(updateCustomerAccountDto));
  }

  @ApiResponse(responseCode = "200", description = "OK", content = @Content)
  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Update a Customer Account Status by ID")
  @PatchMapping("/{id}/update-account-status")
  public ResponseEntity<CustomerAccountDto> updateAccountStatus(
      @PathVariable @Parameter(description = "ID of the Customer Account") Long id,
      @PathVariable @Parameter(description = "ID of the Customer") Long customerId,
      @RequestBody @Valid UpdateCustomerAccountStatusDto updateCustomerAccountDto) {
    updateCustomerAccountDto.setId(id);
    updateCustomerAccountDto.setCustomerId(customerId);
    return ResponseEntity.ok(updateCustomerAccountStatusCommand.execute(updateCustomerAccountDto));
  }

  @ApiResponse(responseCode = "200", description = "OK", content = @Content)
  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Get a Customer Account by ID")
  @GetMapping("/{id}")
  public ResponseEntity<CustomerAccountDto> findByIdAndCustomerId(
      @PathVariable @Parameter(description = "ID of the Customer Account") Long id,
      @PathVariable @Parameter(description = "ID of the Customer") Long customerId) {

    FindByIdAndCustomerIdFilterDto filterDto = FindByIdAndCustomerIdFilterDto.builder()
        .id(id)
        .customerId(customerId)
        .build();

    return ResponseEntity.ok(findByIdCustomerAccountCommand.execute(filterDto));
  }

  @ApiResponse(responseCode = "200", description = "OK", content = @Content)
  @ApiResponse(responseCode = "204", description = "No Content")
  @Operation(summary = "Get All Accounts by Customer Id")
  @GetMapping
  public ResponseEntity<List<CustomerAccountDto>> findAllByCustomerId(
      @PathVariable @Parameter(description = "ID of the customer") Long customerId) {

    List<CustomerAccountDto> accountDto = findAllCustomerAccountCommand.execute(customerId);

    return accountDto != null && !accountDto.isEmpty() ? ResponseEntity.ok(accountDto)
        : ResponseEntity.noContent().build();
  }
}
