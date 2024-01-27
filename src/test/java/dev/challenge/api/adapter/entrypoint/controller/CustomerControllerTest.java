package dev.challenge.api.adapter.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customer.CheckCustomerPasswordDto;
import dev.challenge.api.adapter.entrypoint.dto.customer.CreateCustomerDto;
import dev.challenge.api.adapter.entrypoint.dto.customer.CustomerDto;
import dev.challenge.api.adapter.entrypoint.dto.customer.UpdateCustomerDto;
import dev.challenge.api.domain.enumeration.CustomerTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
class CustomerControllerTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  private CustomerController customerController;

  @Mock
  private ServiceCommand<CreateCustomerDto, CustomerDto> createCustomerCommand;

  @Mock
  private ServiceCommand<UpdateCustomerDto, CustomerDto> updateCustomerCommand;

  @Mock
  private ServiceCommand<Long, CustomerDto> findByIdCommand;

  @Mock
  private ServiceCommand<CheckCustomerPasswordDto, Boolean> checkCustomerPasswordCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);  // Inicialize os mocks

    customerController = new CustomerController(createCustomerCommand, updateCustomerCommand, findByIdCommand, checkCustomerPasswordCommand);

    mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void testCreateCustomer() throws Exception {
    // Arrange
    CreateCustomerDto createCustomerDto = CreateCustomerDto.builder()
        .customerType(CustomerTypeEnum.NATURAL_PERSON)
        .documentNumber("12345678901")
        .name("John Doe")
        .email("john.doe@example.com")
        .phoneNumber("12345678901")
        .password("Password123!")
        .build();
    CustomerDto responseCustomerDto = new CustomerDto();
    responseCustomerDto.setId(1L);

    when(createCustomerCommand.execute(any())).thenReturn(responseCustomerDto);

    // Act & Assert
    mockMvc.perform(post("/api/v1/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(createCustomerDto))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(responseCustomerDto.getId()));
  }

  @Test
  void testUpdateCustomer() throws Exception {
    // Arrange
    UpdateCustomerDto updateCustomerDto = UpdateCustomerDto.builder()
        .name("John Doe")
        .email("john.doe@example.com")
        .phoneNumber("11123456789")
        .build();
    CustomerDto responseCustomerDto = new CustomerDto();
    responseCustomerDto.setId(1L);

    when(updateCustomerCommand.execute(any())).thenReturn(responseCustomerDto);

    // Act & Assert
    mockMvc.perform(patch("/api/v1/customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(updateCustomerDto))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(responseCustomerDto.getId()));
  }


  @Test
  void testGetCustomerById() throws Exception {
    // Arrange
    Long customerId = 1L;
    CustomerDto responseCustomerDto = new CustomerDto();
    responseCustomerDto.setId(customerId);

    when(findByIdCommand.execute(customerId)).thenReturn(responseCustomerDto);

    // Act & Assert
    mockMvc.perform(get("/api/v1/customers/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(responseCustomerDto.getId()));
  }

  @Test
  void testCheckCustomerPassword() throws Exception {
    // Arrange
    Long customerId = 1L;
    CheckCustomerPasswordDto checkCustomerPasswordDto = new CheckCustomerPasswordDto();
    checkCustomerPasswordDto.setId(customerId);
    checkCustomerPasswordDto.setPassword("Password123!");

    when(checkCustomerPasswordCommand.execute(any())).thenReturn(true);

    // Act & Assert
    mockMvc.perform(post("/api/v1/customers/1/check-password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(checkCustomerPasswordDto))))
        .andExpect(status().isNoContent());
  }
}
