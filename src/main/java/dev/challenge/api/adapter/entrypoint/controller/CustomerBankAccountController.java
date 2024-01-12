package dev.challenge.api.adapter.entrypoint.controller;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CreateCustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.UpdateCustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.CreateCustomerBankAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.CustomerBankAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.UpdateCustomerBankAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.filter.FindByIdAndCustomerIdFilterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/api/v1/customers/{customerId}/customer-bank-accounts")
@Tag(name = "Customer Bank Accounts", description = "Operations related to Customer Bank Accounts")
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
@io.swagger.v3.oas.annotations.media.Schema(
    name = "application/json",
    implementation = CustomerAddressController.class
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerBankAccountController {

  private final ServiceCommand<CreateCustomerBankAccountDto, CustomerBankAccountDto> createCustomerBankAccountCommand;
  private final ServiceCommand<UpdateCustomerBankAccountDto, CustomerBankAccountDto> updateCustomerBankAccountCommand;
  private final ServiceCommand<FindByIdAndCustomerIdFilterDto, CustomerBankAccountDto> findByIdCustomerBankAccountCommand;
  private final ServiceCommand<Long, List<CustomerBankAccountDto>> findAllCustomerBankAccountCommand;

  @Operation(summary = "Create a Customer Bank Account")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CustomerBankAccountDto> create(
      @PathVariable @Parameter(description = "ID of the customer") Long customerId,
      @RequestBody CreateCustomerBankAccountDto createCustomerBankAccountDto) {

    createCustomerBankAccountDto.setCustomerId(customerId);
    CustomerBankAccountDto createdCustomerBankAccount = createCustomerBankAccountCommand.execute(createCustomerBankAccountDto);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(createdCustomerBankAccount.getId()).toUri();

    return ResponseEntity.created(location).body(createdCustomerBankAccount);
  }

  @Operation(summary = "Update a Customer Bank Account by ID")
  @PatchMapping("/{id}")
  public ResponseEntity<CustomerBankAccountDto> update(
      @PathVariable @Parameter(description = "ID of the Customer Bank Account") Long id,
      @PathVariable @Parameter(description = "ID of the customer") Long customerId,
      @RequestBody UpdateCustomerBankAccountDto updateCustomerBankAccountDto) {

    updateCustomerBankAccountDto.setId(id);
    updateCustomerBankAccountDto.setCustomerId(customerId);

    CustomerBankAccountDto updatedCustomerBankAccount = updateCustomerBankAccountCommand.execute(updateCustomerBankAccountDto);

    return updatedCustomerBankAccount != null ? ResponseEntity.ok(updatedCustomerBankAccount)
        : ResponseEntity.notFound().build();
  }

  @Operation(summary = "Get a Customer Bank Account by ID")
  @GetMapping("/{id}")
  public ResponseEntity<CustomerBankAccountDto> findByIdAndCustomerId(
      @PathVariable @Parameter(description = "ID of the Customer Bank Account") Long id,
      @PathVariable @Parameter(description = "ID of the customer") Long customerId) {

    FindByIdAndCustomerIdFilterDto filter = FindByIdAndCustomerIdFilterDto.builder()
        .id(id)
        .customerId(customerId)
        .build();

    CustomerBankAccountDto customerBankAccount = findByIdCustomerBankAccountCommand.execute(filter);

    return customerBankAccount != null ? ResponseEntity.ok(customerBankAccount)
        : ResponseEntity.notFound().build();
  }

  @Operation(summary = "Get All Customer Bank Account")
  @GetMapping
  public ResponseEntity<List<CustomerBankAccountDto>> findAllByCustomerId(
      @PathVariable @Parameter(description = "ID of the customer") Long customerId) {

    List<CustomerBankAccountDto> customerBankAccount = findAllCustomerBankAccountCommand.execute(customerId);

    return customerBankAccount != null && !customerBankAccount.isEmpty()
        ? ResponseEntity.ok(customerBankAccount)
        : ResponseEntity.noContent().build();
  }
}
