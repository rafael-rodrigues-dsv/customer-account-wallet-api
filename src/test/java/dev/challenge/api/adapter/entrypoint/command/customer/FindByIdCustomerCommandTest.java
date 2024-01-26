package dev.challenge.api.adapter.entrypoint.command.customer;

import dev.challenge.api.adapter.entrypoint.dto.customer.CustomerDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerService;
import dev.challenge.api.domain.model.CustomerModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdCustomerCommandTest {

  @Mock
  private CustomerService customerService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private FindByIdCustomerCommand findByIdCustomerCommand;

  @Test
  void testExecute() {
    // Arrange
    Long customerId = 1L;

    CustomerModel customerModel = new CustomerModel();
    customerModel.setId(customerId);
    customerModel.setName("John Doe");
    customerModel.setEmail("john.doe@example.com");
    customerModel.setPhoneNumber("123456789");

    CustomerDto expectedCustomerDto = new CustomerDto();
    expectedCustomerDto.setId(customerId);
    expectedCustomerDto.setName("John Doe");
    expectedCustomerDto.setEmail("john.doe@example.com");
    expectedCustomerDto.setPhoneNumber("123456789");

    when(customerService.findById(customerId)).thenReturn(customerModel);
    when(customMapper.map(customerModel, CustomerDto.class)).thenReturn(expectedCustomerDto);

    // Act
    CustomerDto result = findByIdCustomerCommand.execute(customerId);

    // Assert
    assertEquals(expectedCustomerDto, result);
  }
}
