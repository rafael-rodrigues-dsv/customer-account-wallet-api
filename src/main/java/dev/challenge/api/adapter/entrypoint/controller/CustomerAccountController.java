package dev.challenge.api.adapter.entrypoint.controller;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CreateCustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountDto;
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
@RequestMapping(value = "/api/v1/customers/{customerId}/customer-accounts")
@Tag(name = "Customer Accounts", description = "Operations related to Customer Accounts")
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
@io.swagger.v3.oas.annotations.media.Schema(
    name = "application/json",
    implementation = CustomerAddressController.class
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerAccountController {

  private final ServiceCommand<CreateCustomerAccountDto, CustomerAccountDto> createCustomerAccountCommand;
  private final ServiceCommand<UpdateCustomerAccountDto, CustomerAccountDto> updateCustomerAccountCommand;
  private final ServiceCommand<FindByIdAndCustomerIdFilterDto, CustomerAccountDto> findByIdCustomerAccountCommand;
  private final ServiceCommand<Long, List<CustomerAccountDto>> findAllCustomerAccountCommand;

  @Operation(summary = "Create a Customer Account")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CustomerAccountDto> create(
      @PathVariable @Parameter(description = "ID of the customer") Long customerId,
      @RequestBody CreateCustomerAccountDto createCustomerAccountDto) {

    createCustomerAccountDto.setCustomerId(customerId);
    CustomerAccountDto createdCustomerAccount = createCustomerAccountCommand.execute(createCustomerAccountDto);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(createdCustomerAccount.getId()).toUri();

    return ResponseEntity.created(location).body(createdCustomerAccount);
  }

  @Operation(summary = "Update a Customer Account by ID")
  @PatchMapping("/{id}")
  public ResponseEntity<CustomerAccountDto> update(
      @PathVariable @Parameter(description = "ID of the Customer Account") Long id,
      @PathVariable @Parameter(description = "ID of the customer") Long customerId,
      @RequestBody UpdateCustomerAccountDto updateCustomerAccountDto) {

    updateCustomerAccountDto.setId(id);
    updateCustomerAccountDto.setCustomerId(customerId);

    CustomerAccountDto updatedCustomerAccount = updateCustomerAccountCommand.execute(updateCustomerAccountDto);

    return updatedCustomerAccount != null ? ResponseEntity.ok(updatedCustomerAccount)
        : ResponseEntity.notFound().build();
  }

  @Operation(summary = "Get a Customer Account by ID")
  @GetMapping("/{id}")
  public ResponseEntity<CustomerAccountDto> findByIdAndCustomerId(
      @PathVariable @Parameter(description = "ID of the Customer Account") Long id,
      @PathVariable @Parameter(description = "ID of the customer") Long customerId) {

    FindByIdAndCustomerIdFilterDto filter = FindByIdAndCustomerIdFilterDto.builder()
        .id(id)
        .customerId(customerId)
        .build();

    CustomerAccountDto customerAccount = findByIdCustomerAccountCommand.execute(filter);

    return customerAccount != null ? ResponseEntity.ok(customerAccount)
        : ResponseEntity.notFound().build();
  }

  @Operation(summary = "Get All Customer Accounts")
  @GetMapping
  public ResponseEntity<List<CustomerAccountDto>> findAllByCustomerId(
      @PathVariable @Parameter(description = "ID of the customer") Long customerId) {

    List<CustomerAccountDto> customerAccount = findAllCustomerAccountCommand.execute(customerId);

    return customerAccount != null && !customerAccount.isEmpty()
        ? ResponseEntity.ok(customerAccount)
        : ResponseEntity.noContent().build();
  }
}