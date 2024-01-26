package dev.challenge.api.adapter.entrypoint.command.customer;

import dev.challenge.api.adapter.entrypoint.dto.customer.CheckCustomerPasswordDto;
import dev.challenge.api.domain.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckCustomerPasswordCommandTest {

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private CheckCustomerPasswordCommand checkCustomerPasswordCommand;

  @Test
  void testExecuteWithCorrectPassword() {
    // Arrange
    CheckCustomerPasswordDto dto = new CheckCustomerPasswordDto();
    dto.setId(1L);
    dto.setPassword("correctPassword");

    when(customerService.checkPassword(dto.getId(), dto.getPassword())).thenReturn(true);

    // Act
    boolean result = checkCustomerPasswordCommand.execute(dto);

    // Assert
    assertTrue(result);
  }

  @Test
  void testExecuteWithIncorrectPassword() {
    // Arrange
    CheckCustomerPasswordDto dto = new CheckCustomerPasswordDto();
    dto.setId(1L);
    dto.setPassword("incorrectPassword");

    when(customerService.checkPassword(dto.getId(), dto.getPassword())).thenReturn(false);

    // Act
    boolean result = checkCustomerPasswordCommand.execute(dto);

    // Assert
    assertFalse(result);
  }
}
