package dev.challenge.api.adapter.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CreateCustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.UpdateCustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.filter.FindByIdAndCustomerIdFilterDto;
import dev.challenge.api.domain.enumeration.CustomerAddressTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ComponentScan("dev.challenge.api.adapter.entrypoint.controller")
class CustomerAddressControllerTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  private CustomerAddressController customerAddressController;

  @Mock
  private ServiceCommand<CreateCustomerAddressDto, CustomerAddressDto> createCustomerAddressCommand;

  @Mock
  private ServiceCommand<UpdateCustomerAddressDto, CustomerAddressDto> updateCustomerAddressCommand;

  @Mock
  private ServiceCommand<FindByIdAndCustomerIdFilterDto, CustomerAddressDto> findByIdCustomerAddressCommand;

  @Mock
  private ServiceCommand<Long, List<CustomerAddressDto>> findAllCustomerAddressCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    customerAddressController = new CustomerAddressController(
        createCustomerAddressCommand, updateCustomerAddressCommand,
        findByIdCustomerAddressCommand, findAllCustomerAddressCommand);

    mockMvc = MockMvcBuilders.standaloneSetup(customerAddressController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void testCreateCustomerAddress() throws Exception {
    // Arrange
    CreateCustomerAddressDto createAddressDto = CreateCustomerAddressDto.builder()
        .customerId(1L)
        .street("123 Main St")
        .city("Cityville")
        .state("State")
        .zipCode("1234567890")
        .addressType(CustomerAddressTypeEnum.RESIDENTIAL)
        .build();

    CustomerAddressDto createdAddress = new CustomerAddressDto();
    createdAddress.setId(1L);

    when(createCustomerAddressCommand.execute(any())).thenReturn(createdAddress);

    // Act & Assert
    mockMvc.perform(post("/api/v1/customers/1/customer-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(createAddressDto))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(createdAddress.getId()));
  }

  @Test
  void testUpdateCustomerAddress() throws Exception {
    // Arrange
    UpdateCustomerAddressDto updateAddressDto = UpdateCustomerAddressDto.builder()
        .id(1L)
        .customerId(1L)
        .street("123 Main St")
        .city("City")
        .state("State")
        .zipCode("1234567890")
        .addressType(CustomerAddressTypeEnum.RESIDENTIAL)
        .build();

    CustomerAddressDto updatedAddress = new CustomerAddressDto();
    updatedAddress.setId(1L);

    when(updateCustomerAddressCommand.execute(any())).thenReturn(updatedAddress);

    // Act & Assert
    mockMvc.perform(patch("/api/v1/customers/1/customer-addresses/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(updateAddressDto))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(updatedAddress.getId()));
  }

  @Test
  void testFindByIdAndCustomerId() throws Exception {
    // Arrange
    Long id = 1L;
    Long customerId = 1L;

    FindByIdAndCustomerIdFilterDto filterDto = FindByIdAndCustomerIdFilterDto.builder()
        .id(id)
        .customerId(customerId)
        .build();

    CustomerAddressDto addressDto = CustomerAddressDto.builder()
        .id(id)
        .build();

    when(findByIdCustomerAddressCommand.execute(any())).thenReturn(addressDto);

    // Act & Assert
    mockMvc.perform(get("/api/v1/customers/1/customer-addresses/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(addressDto.getId()));
  }

  @Test
  void testFindAllByCustomerId() throws Exception {
    // Arrange
    Long customerId = 1L;

    List<CustomerAddressDto> customerAddresses = List.of(new CustomerAddressDto());

    when(findAllCustomerAddressCommand.execute(customerId)).thenReturn(customerAddresses);

    // Act & Assert
    mockMvc.perform(get("/api/v1/customers/1/customer-addresses"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(customerAddresses.get(0).getId()));
  }

  @Test
  void testFindAllByCustomerIdNoAddresses() throws Exception {
    // Arrange
    Long customerId = 1L;

    when(findAllCustomerAddressCommand.execute(customerId)).thenReturn(Collections.emptyList());

    // Act & Assert
    mockMvc.perform(get("/api/v1/customers/1/customer-addresses"))
        .andExpect(status().isNoContent());
  }
}
