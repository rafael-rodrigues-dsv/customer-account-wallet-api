package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.filter.FindByIdAndCustomerIdFilterDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.model.CustomerAccountModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdCustomerAccountCommandTest {

  @Mock
  private CustomerAccountService customerAccountService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private FindByIdCustomerAccountCommand findByIdCustomerAccountCommand;

  @Test
  void testExecute() {
    // Arrange
    Long accountId = 1L;
    Long customerId = 1L;

    FindByIdAndCustomerIdFilterDto filterDto = new FindByIdAndCustomerIdFilterDto();
    filterDto.setId(accountId);
    filterDto.setCustomerId(customerId);

    CustomerAccountModel expectedCustomerAccountModel = new CustomerAccountModel();
    expectedCustomerAccountModel.setId(accountId);

    CustomerAccountDto expectedCustomerAccountDto = new CustomerAccountDto();
    expectedCustomerAccountDto.setId(accountId);

    when(customerAccountService.findByIdAndVerifyCustomerId(accountId, customerId)).thenReturn(expectedCustomerAccountModel);
    when(customMapper.map(expectedCustomerAccountModel, CustomerAccountDto.class)).thenReturn(expectedCustomerAccountDto);

    // Act
    CustomerAccountDto result = findByIdCustomerAccountCommand.execute(filterDto);

    // Assert
    assertEquals(expectedCustomerAccountDto, result);
  }
}
