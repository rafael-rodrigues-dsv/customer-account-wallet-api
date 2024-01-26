package dev.challenge.api.adapter.entrypoint.command.customeraddress;

import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllCustomerAddressCommandTest {

  @Mock
  private CustomerAddressService customerAddressService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private FindAllCustomerAddressCommand findAllCustomerAddressCommand;

  @Test
  void testExecute() {
    // Arrange
    Long customerId = 1L;

    CustomerAddressModel addressModel = new CustomerAddressModel();
    addressModel.setId(1L);

    List<CustomerAddressModel> expectedAddressModels = List.of(addressModel);

    when(customerAddressService.findAllByCustomerId(customerId)).thenReturn(expectedAddressModels);

    CustomerAddressDto addressDto = new CustomerAddressDto();
    addressDto.setId(1L);

    List<CustomerAddressDto> expectedAddressDto = List.of(addressDto);

    when(customMapper.map(expectedAddressModels, List.class)).thenReturn(expectedAddressDto);

    // Act
    List<CustomerAddressDto> result = findAllCustomerAddressCommand.execute(customerId);

    // Assert
    assertEquals(expectedAddressDto, result);
  }
}
