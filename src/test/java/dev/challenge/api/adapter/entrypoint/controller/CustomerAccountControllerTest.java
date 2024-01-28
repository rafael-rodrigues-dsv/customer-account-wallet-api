package dev.challenge.api.adapter.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CreateCustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountBalanceDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountStatusDto;
import dev.challenge.api.adapter.entrypoint.dto.filter.FindByIdAndCustomerIdFilterDto;
import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
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
class CustomerAccountControllerTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  private CustomerAccountController customerAccountController;

  @Mock
  private ServiceCommand<CreateCustomerAccountDto, CustomerAccountDto> createCustomerAccountCommand;

  @Mock
  private ServiceCommand<UpdateCustomerAccountDto, CustomerAccountDto> updateCustomerAccountCommand;

  @Mock
  private ServiceCommand<UpdateCustomerAccountBalanceDto, CustomerAccountDto> updateCustomerAccountBalanceCommand;

  @Mock
  private ServiceCommand<UpdateCustomerAccountStatusDto, CustomerAccountDto> updateCustomerAccountStatusCommand;

  @Mock
  private ServiceCommand<FindByIdAndCustomerIdFilterDto, CustomerAccountDto> findByIdCustomerAccountCommand;

  @Mock
  private ServiceCommand<Long, List<CustomerAccountDto>> findAllCustomerAccountCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    customerAccountController = new CustomerAccountController(
        createCustomerAccountCommand, updateCustomerAccountCommand,
        updateCustomerAccountBalanceCommand, updateCustomerAccountStatusCommand,
        findByIdCustomerAccountCommand, findAllCustomerAccountCommand);

    mockMvc = MockMvcBuilders.standaloneSetup(customerAccountController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void testCreateCustomerAccount() throws Exception {
    // Arrange
    CreateCustomerAccountDto createAccountDto = CreateCustomerAccountDto.builder()
        .customerId(1L)
        .agency("123ABC")
        .accountNumber("789XYZ")
        .build();

    CustomerAccountDto createdAccount = new CustomerAccountDto();
    createdAccount.setId(1L);

    when(createCustomerAccountCommand.execute(any())).thenReturn(createdAccount);

    // Act & Assert
    mockMvc.perform(post("/api/v1/customers/1/customer-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(createAccountDto))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(createdAccount.getId()));
  }

  @Test
  void testUpdateCustomerAccount() throws Exception {
    // Arrange
    UpdateCustomerAccountDto updateAccountDto = UpdateCustomerAccountDto.builder()
        .id(1L)
        .customerId(2L)
        .agency("123ABC")
        .accountNumber("987XYZ")
        .build();

    CustomerAccountDto updatedAccount = new CustomerAccountDto();
    updatedAccount.setId(1L);

    when(updateCustomerAccountCommand.execute(any())).thenReturn(updatedAccount);

    // Act & Assert
    mockMvc.perform(patch("/api/v1/customers/1/customer-accounts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(updateAccountDto))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(updatedAccount.getId()));
  }

  @Test
  void testUpdateCustomerAccountBalance() throws Exception {
    // Arrange
    UpdateCustomerAccountBalanceDto updateBalanceDto = UpdateCustomerAccountBalanceDto.builder()
        .id(1L)
        .customerId(1L)
        .balance(BigDecimal.TEN)
        .build();

    CustomerAccountDto updatedAccount = new CustomerAccountDto();
    updatedAccount.setId(1L);

    when(updateCustomerAccountBalanceCommand.execute(any())).thenReturn(updatedAccount);

    // Act & Assert
    mockMvc.perform(patch("/api/v1/customers/1/customer-accounts/1/update-balance")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(updateBalanceDto))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(updatedAccount.getId()));
  }

  @Test
  void testUpdateCustomerAccountStatus() throws Exception {
    // Arrange
    UpdateCustomerAccountStatusDto updateStatusDto = UpdateCustomerAccountStatusDto.builder()
        .id(1L)
        .customerId(1L)
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .build();;

    CustomerAccountDto updatedAccount = new CustomerAccountDto();
    updatedAccount.setId(1L);

    when(updateCustomerAccountStatusCommand.execute(any())).thenReturn(updatedAccount);

    // Act & Assert
    mockMvc.perform(patch("/api/v1/customers/1/customer-accounts/1/update-account-status")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(updateStatusDto))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(updatedAccount.getId()));
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

    CustomerAccountDto accountDto = CustomerAccountDto.builder()
        .id(id)
        .build();

    when(findByIdCustomerAccountCommand.execute(any())).thenReturn(accountDto);

    // Act & Assert
    mockMvc.perform(get("/api/v1/customers/1/customer-accounts/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(accountDto.getId()));
  }

  @Test
  void testFindAllByCustomerId() throws Exception {
    // Arrange
    Long customerId = 1L;

    List<CustomerAccountDto> customerAccounts = List.of(new CustomerAccountDto());

    when(findAllCustomerAccountCommand.execute(customerId)).thenReturn(customerAccounts);

    // Act & Assert
    mockMvc.perform(get("/api/v1/customers/1/customer-accounts"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(customerAccounts.get(0).getId()));
  }

  @Test
  void testFindAllByCustomerIdNoAccounts() throws Exception {
    // Arrange
    Long customerId = 1L;

    when(findAllCustomerAccountCommand.execute(customerId)).thenReturn(Collections.emptyList());

    // Act & Assert
    mockMvc.perform(get("/api/v1/customers/1/customer-accounts"))
        .andExpect(status().isNoContent());
  }
}
