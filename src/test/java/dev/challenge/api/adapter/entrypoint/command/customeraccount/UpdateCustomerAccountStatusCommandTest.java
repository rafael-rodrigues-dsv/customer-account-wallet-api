package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountStatusDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import dev.challenge.api.domain.model.CustomerAccountModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerAccountStatusCommandTest {
  @Mock
  private CustomerAccountService customerAccountService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private UpdateCustomerAccountStatusCommand updateCustomerAccountStatusCommand;

  @Test
  void testExecute() {
    // Arrange
    UpdateCustomerAccountStatusDto updateAccountStatusDto = new UpdateCustomerAccountStatusDto();
    updateAccountStatusDto.setId(1L);
    updateAccountStatusDto.setCustomerId(1L);
    updateAccountStatusDto.setAccountStatus(CustomerAccountStatusEnum.ACTIVE);

    CustomerAccountModel expectedAccountModel = new CustomerAccountModel();
    expectedAccountModel.setId(1L);

    CustomerAccountDto expectedAccountDto = new CustomerAccountDto();
    expectedAccountDto.setId(1L);


    when(customerAccountService.updateAccountStatus(eq(1L), eq(1L), eq(CustomerAccountStatusEnum.ACTIVE))).thenReturn(expectedAccountModel);
    when(customMapper.map(expectedAccountModel, CustomerAccountDto.class)).thenReturn(expectedAccountDto);

    // Act
    CustomerAccountDto result = updateCustomerAccountStatusCommand.execute(updateAccountStatusDto);

    // Assert
    assertEquals(expectedAccountDto, result);
  }
}