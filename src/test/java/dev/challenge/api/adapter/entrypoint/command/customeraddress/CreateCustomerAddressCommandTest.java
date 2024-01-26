package dev.challenge.api.adapter.entrypoint.command.customeraddress;

import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CreateCustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCustomerAddressCommandTest {

  @Mock
  private CustomerAddressService customerAddressService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private CreateCustomerAddressCommand createCustomerAddressCommand;

  @Test
  void testExecute() {
    // Arrange
    CreateCustomerAddressDto createAddressDto = new CreateCustomerAddressDto();
    createAddressDto.setCustomerId(1L);

    CustomerAddressModel expectedAddressModel = new CustomerAddressModel();
    expectedAddressModel.setId(1L);

    CustomerAddressDto expectedAddressDto = new CustomerAddressDto();
    expectedAddressDto.setId(1L);

    when(customMapper.map(createAddressDto, CustomerAddressModel.class)).thenReturn(expectedAddressModel);
    when(customerAddressService.add(eq(createAddressDto.getCustomerId()), any(CustomerAddressModel.class)))
        .thenReturn(expectedAddressModel);
    when(customMapper.map(expectedAddressModel, CustomerAddressDto.class)).thenReturn(expectedAddressDto);

    // Act
    CustomerAddressDto result = createCustomerAddressCommand.execute(createAddressDto);

    // Assert
    assertEquals(expectedAddressDto, result);
  }
}
