package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountBalanceDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.model.CustomerAccountModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerAccountBalanceCommandTest {
  @Mock
  private CustomerAccountService customerAccountService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private UpdateCustomerAccountBalanceCommand updateCustomerAccountBalanceCommand;


    @Test
    void testExecute() {
      // Arrange
      UpdateCustomerAccountBalanceDto updateAccountBalanceDto = new UpdateCustomerAccountBalanceDto();
      updateAccountBalanceDto.setId(1L);
      updateAccountBalanceDto.setCustomerId(1L);
      updateAccountBalanceDto.setBalance(BigDecimal.TEN);

      CustomerAccountModel expectedAccountModel = new CustomerAccountModel();
      expectedAccountModel.setId(1L);

      CustomerAccountDto expectedAccountDto = new CustomerAccountDto();
      expectedAccountDto.setId(1L);


      when(customerAccountService.updateBalance(eq(1L), eq(1L),eq(BigDecimal.TEN))).thenReturn(expectedAccountModel);
      when(customMapper.map(expectedAccountModel, CustomerAccountDto.class)).thenReturn(expectedAccountDto);

      // Act
      CustomerAccountDto result = updateCustomerAccountBalanceCommand.execute(updateAccountBalanceDto);

      // Assert
      assertEquals(expectedAccountDto, result);
    }
}