package dev.challenge.api.adapter.entrypoint.command.customer;

import dev.challenge.api.adapter.entrypoint.dto.customer.CustomerDto;
import dev.challenge.api.adapter.entrypoint.dto.customer.UpdateCustomerDto;
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
class UpdateCustomerCommandTest {

  @Mock
  private CustomerService customerService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private UpdateCustomerCommand updateCustomerCommand;

  @Test
  void testExecute() {
    // Arrange
    UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto();
    updateCustomerDto.setId(1L);
    updateCustomerDto.setName("John Doe");
    updateCustomerDto.setEmail("john.doe@example.com");
    updateCustomerDto.setPhoneNumber("123456789");

    CustomerModel updatedCustomerModel = new CustomerModel();
    updatedCustomerModel.setId(updateCustomerDto.getId());
    updatedCustomerModel.setName(updateCustomerDto.getName());
    updatedCustomerModel.setEmail(updateCustomerDto.getEmail());
    updatedCustomerModel.setPhoneNumber(updateCustomerDto.getPhoneNumber());

    CustomerDto expectedCustomerDto = new CustomerDto();
    expectedCustomerDto.setId(updateCustomerDto.getId());
    expectedCustomerDto.setName(updateCustomerDto.getName());
    expectedCustomerDto.setEmail(updateCustomerDto.getEmail());
    expectedCustomerDto.setPhoneNumber(updateCustomerDto.getPhoneNumber());

    when(customMapper.map(updateCustomerDto, CustomerModel.class)).thenReturn(updatedCustomerModel);
    when(customerService.update(updateCustomerDto.getId(), updatedCustomerModel)).thenReturn(updatedCustomerModel);
    when(customMapper.map(updatedCustomerModel, CustomerDto.class)).thenReturn(expectedCustomerDto);

    // Act
    CustomerDto result = updateCustomerCommand.execute(updateCustomerDto);

    // Assert
    assertEquals(expectedCustomerDto, result);
  }
}
