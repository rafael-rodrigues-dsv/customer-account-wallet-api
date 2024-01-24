package dev.challenge.api.adapter.entrypoint.controller;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customer.CreateCustomerDto;
import dev.challenge.api.adapter.entrypoint.dto.customer.CustomerDto;
import dev.challenge.api.adapter.entrypoint.dto.customer.UpdateCustomerDto;
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

@RestController
@RequestMapping(value = "/api/v1/customers")
@Tag(name = "Customers", description = "Operations related to Customers")
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

  private final ServiceCommand<CreateCustomerDto, CustomerDto> createCustomerCommand;
  private final ServiceCommand<UpdateCustomerDto, CustomerDto> updateCustomerCommand;
  private final ServiceCommand<Long, CustomerDto> findByIdCommand;

  @Operation(summary = "Create a Customer")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CustomerDto> create(
      @RequestBody @Parameter(description = "Customer data to be created")
      CreateCustomerDto createCustomerDto) {

    CustomerDto createdCustomer = createCustomerCommand.execute(createCustomerDto);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(createdCustomer.getId()).toUri();

    return ResponseEntity.created(location).body(createdCustomer);
  }

  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Update a Customer by ID")
  @PatchMapping("/{id}")
  public ResponseEntity<CustomerDto> update(
      @PathVariable @Parameter(description = "ID of the customer to be updated") Long id,
      @Valid @RequestBody UpdateCustomerDto updateCustomerDto) {

    updateCustomerDto.setId(id);
    CustomerDto updatedCustomer = updateCustomerCommand.execute(updateCustomerDto);

    return updatedCustomer != null ? ResponseEntity.ok(updatedCustomer)
        : ResponseEntity.notFound().build();
  }

  @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
  @Operation(summary = "Get a Customer by ID")
  @GetMapping("/{id}")
  public ResponseEntity<CustomerDto> getById(
      @PathVariable @Parameter(description = "ID of the customer to be retrieved") Long id) {

    CustomerDto customer = findByIdCommand.execute(id);

    return customer != null ? ResponseEntity.ok(customer)
        : ResponseEntity.notFound().build();
  }
}
