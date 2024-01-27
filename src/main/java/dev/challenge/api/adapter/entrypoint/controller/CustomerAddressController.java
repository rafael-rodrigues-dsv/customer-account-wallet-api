package dev.challenge.api.adapter.entrypoint.controller;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CreateCustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.UpdateCustomerAddressDto;
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
@RequestMapping(value = "/api/v1/customers/{customerId}/customer-addresses", produces = "application/json")
@Tag(name = "Customer Addresses", description = "Operations related to Customer Addresses")
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerAddressController {

  private final ServiceCommand<CreateCustomerAddressDto, CustomerAddressDto> createCustomerAddressCommand;
  private final ServiceCommand<UpdateCustomerAddressDto, CustomerAddressDto> updateCustomerAddressCommand;
  private final ServiceCommand<FindByIdAndCustomerIdFilterDto, CustomerAddressDto> findByIdCustomerAddressCommand;
  private final ServiceCommand<Long, List<CustomerAddressDto>> findAllCustomerAddressCommand;

  @Operation(summary = "Create a Customer Address")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CustomerAddressDto> create(
      @PathVariable @Parameter(description = "ID of the customer") Long customerId,
      @RequestBody @Valid CreateCustomerAddressDto createAddressDto) {

    createAddressDto.setCustomerId(customerId);
    CustomerAddressDto createdAddress = createCustomerAddressCommand.execute(createAddressDto);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(createdAddress.getId()).toUri();

    return ResponseEntity.created(location).body(createdAddress);
  }

  @ApiResponse(responseCode = "200", description = "OK", content = @Content)
  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Update a Customer Address by ID")
  @PatchMapping("/{id}")
  public ResponseEntity<CustomerAddressDto> update(
      @PathVariable @Parameter(description = "ID of the customer address") Long id,
      @PathVariable @Parameter(description = "ID of the customer") Long customerId,
      @RequestBody @Valid UpdateCustomerAddressDto updateAddressDto) {
    updateAddressDto.setId(id);
    updateAddressDto.setCustomerId(customerId);
    return ResponseEntity.ok(updateCustomerAddressCommand.execute(updateAddressDto));
  }

  @ApiResponse(responseCode = "200", description = "OK", content = @Content)
  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Get a Customer Address by ID")
  @GetMapping("/{id}")
  public ResponseEntity<CustomerAddressDto> findByIdAndCustomerId(
      @PathVariable @Parameter(description = "ID of the customer address") Long id,
      @PathVariable @Parameter(description = "ID of the customer") Long customerId) {

    FindByIdAndCustomerIdFilterDto filterDto = FindByIdAndCustomerIdFilterDto.builder()
        .id(id)
        .customerId(customerId)
        .build();

    return ResponseEntity.ok(findByIdCustomerAddressCommand.execute(filterDto));
  }

  @ApiResponse(responseCode = "200", description = "OK", content = @Content)
  @ApiResponse(responseCode = "204", description = "No Content")
  @Operation(summary = "Get All Customer Addresses by Customer Id")
  @GetMapping
  public ResponseEntity<List<CustomerAddressDto>> findAllByCustomerId(
      @PathVariable @Parameter(description = "ID of the customer") Long customerId) {

    List<CustomerAddressDto> customerAddress = findAllCustomerAddressCommand.execute(customerId);

    return customerAddress != null && !customerAddress.isEmpty()
        ? ResponseEntity.ok(customerAddress)
        : ResponseEntity.noContent().build();
  }
}
