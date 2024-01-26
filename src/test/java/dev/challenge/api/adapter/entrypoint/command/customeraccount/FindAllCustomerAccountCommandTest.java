package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.model.CustomerAccountModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllCustomerAccountCommandTest {

  @Mock
  private CustomerAccountService customerAccountService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private FindAllCustomerAccountCommand findAllCustomerAccountCommand;

  @Test
  void testExecute() {
    // Arrange
    Long customerId = 1L;

    CustomerAccountModel accountModel = new CustomerAccountModel();
    accountModel.setId(1L);

    List<CustomerAccountModel> expectedAccountModels = List.of(accountModel);

    when(customerAccountService.findAllByCustomerId(customerId)).thenReturn(expectedAccountModels);

    CustomerAccountDto accountDto = new CustomerAccountDto();
    accountDto.setId(1L);

    List<CustomerAccountDto> expectedAccountDto = List.of(accountDto);

    when(customMapper.map(expectedAccountModels, List.class)).thenReturn(expectedAccountDto);

    // Act
    List<CustomerAccountDto> result = findAllCustomerAccountCommand.execute(customerId);

    // Assert
    assertEquals(expectedAccountDto, result);
  }
}
