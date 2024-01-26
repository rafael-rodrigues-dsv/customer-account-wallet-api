package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.model.CustomerAccountModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerAccountCommandTest {

  @Mock
  private CustomerAccountService customerAccountService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private UpdateCustomerAccountCommand updateCustomerAccountCommand;

  @Test
  void testExecute() {
    // Arrange
    UpdateCustomerAccountDto updateAccountDto = new UpdateCustomerAccountDto();
    updateAccountDto.setId(1L);
    updateAccountDto.setCustomerId(1L);

    CustomerAccountModel expectedAccountModel = new CustomerAccountModel();
    expectedAccountModel.setId(1L);

    CustomerAccountDto expectedAccountDto = new CustomerAccountDto();
    expectedAccountDto.setId(1L);


    when(customMapper.map(updateAccountDto, CustomerAccountModel.class)).thenReturn(expectedAccountModel);
    when(customerAccountService.update(eq(1L), eq(1L), any(CustomerAccountModel.class))).thenReturn(expectedAccountModel);
    when(customMapper.map(expectedAccountModel, CustomerAccountDto.class)).thenReturn(expectedAccountDto);

    // Act
    CustomerAccountDto result = updateCustomerAccountCommand.execute(updateAccountDto);

    // Assert
    assertEquals(expectedAccountDto, result);
  }
}
