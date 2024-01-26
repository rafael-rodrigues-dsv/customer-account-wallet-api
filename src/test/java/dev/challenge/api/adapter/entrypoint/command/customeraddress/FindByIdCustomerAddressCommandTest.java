package dev.challenge.api.adapter.entrypoint.command.customeraddress;

import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.filter.FindByIdAndCustomerIdFilterDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdCustomerAddressCommandTest {

  @Mock
  private CustomerAddressService customerAddressService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private FindByIdCustomerAddressCommand findByIdCustomerAddressCommand;

  @Test
  void testExecute() {
    // Arrange
    Long addressId = 1L;
    Long customerId = 1L;

    FindByIdAndCustomerIdFilterDto filterDto = new FindByIdAndCustomerIdFilterDto();
    filterDto.setId(addressId);
    filterDto.setCustomerId(customerId);

    CustomerAddressModel expectedCustomerAddressModel = new CustomerAddressModel();
    expectedCustomerAddressModel.setId(addressId);

    CustomerAddressDto expectedCustomerAddressDto = new CustomerAddressDto();
    expectedCustomerAddressDto.setId(addressId);

    when(customerAddressService.findByIdAndVerifyCustomerId(addressId, customerId)).thenReturn(expectedCustomerAddressModel);
    when(customMapper.map(expectedCustomerAddressModel, CustomerAddressDto.class)).thenReturn(expectedCustomerAddressDto);

    // Act
    CustomerAddressDto result = findByIdCustomerAddressCommand.execute(filterDto);

    // Assert
    assertEquals(expectedCustomerAddressDto, result);
  }
}
