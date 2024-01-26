package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CreateCustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
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
class CreateCustomerAccountCommandTest {

  @Mock
  private CustomerAccountService customerAccountService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private CreateCustomerAccountCommand createCustomerAccountCommand;

  @Test
  void testExecute() {
    // Arrange
    CreateCustomerAccountDto createCustomerAccountDto = new CreateCustomerAccountDto();
    createCustomerAccountDto.setCustomerId(1L);

    CustomerAccountModel createdCustomerAccountModel = new CustomerAccountModel();
    createdCustomerAccountModel.setId(1L);

    CustomerAccountDto expectedCustomerAccountDto = new CustomerAccountDto();
    expectedCustomerAccountDto.setId(createdCustomerAccountModel.getId());

    when(customMapper.map(createCustomerAccountDto, CustomerAccountModel.class)).thenReturn(createdCustomerAccountModel);
    when(customerAccountService.add(createCustomerAccountDto.getCustomerId(), createdCustomerAccountModel))
        .thenReturn(createdCustomerAccountModel);
    when(customMapper.map(createdCustomerAccountModel, CustomerAccountDto.class)).thenReturn(expectedCustomerAccountDto);

    // Act
    CustomerAccountDto result = createCustomerAccountCommand.execute(createCustomerAccountDto);

    // Assert
    assertEquals(expectedCustomerAccountDto, result);
  }
}
