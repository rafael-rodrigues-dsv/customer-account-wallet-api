package dev.challenge.api.adapter.entrypoint.command.customer;

import dev.challenge.api.adapter.entrypoint.dto.customer.CreateCustomerDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCustomerCommandTest {

  @Mock
  private CustomerService customerService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private CreateCustomerCommand createCustomerCommand;

  @Test
  void testExecute() {
    // Arrange
    CreateCustomerDto createCustomerDto = new CreateCustomerDto();
    createCustomerDto.setName("John Doe");
    createCustomerDto.setEmail("john.doe@example.com");
    createCustomerDto.setPhoneNumber("123456789");

    CustomerModel customerModel = new CustomerModel();
    customerModel.setId(1L);
    customerModel.setName("John Doe");
    customerModel.setEmail("john.doe@example.com");
    customerModel.setPhoneNumber("123456789");

    CustomerDto expectedCustomerDto = new CustomerDto();
    expectedCustomerDto.setId(1L);
    expectedCustomerDto.setName("John Doe");
    expectedCustomerDto.setEmail("john.doe@example.com");
    expectedCustomerDto.setPhoneNumber("123456789");

    when(customMapper.map(createCustomerDto, CustomerModel.class)).thenReturn(customerModel);
    when(customerService.add(any(CustomerModel.class))).thenReturn(customerModel);
    when(customMapper.map(customerModel, CustomerDto.class)).thenReturn(expectedCustomerDto);

    // Act
    CustomerDto result = createCustomerCommand.execute(createCustomerDto);

    // Assert
    assertEquals(expectedCustomerDto, result);
  }
}
