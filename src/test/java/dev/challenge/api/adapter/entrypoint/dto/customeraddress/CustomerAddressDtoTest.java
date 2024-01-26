package dev.challenge.api.adapter.entrypoint.dto.customeraddress;

import dev.challenge.api.domain.enumeration.CustomerAddressTypeEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerAddressDtoTest {

  @Test
  void testBuilder() {
    // Arrange
    CustomerAddressDto customerAddressDto = CustomerAddressDto.builder()
        .id(1L)
        .street("123 Main St")
        .city("City")
        .state("State")
        .zipCode("1234567890")
        .isDefault(true)
        .addressType(CustomerAddressTypeEnum.RESIDENTIAL)
        .build();

    // Assert
    assertThat(customerAddressDto.getId()).isEqualTo(1L);
    assertThat(customerAddressDto.getStreet()).isEqualTo("123 Main St");
    assertThat(customerAddressDto.getCity()).isEqualTo("City");
    assertThat(customerAddressDto.getState()).isEqualTo("State");
    assertThat(customerAddressDto.getZipCode()).isEqualTo("1234567890");
    assertThat(customerAddressDto.getIsDefault()).isEqualTo(true);
    assertThat(customerAddressDto.getAddressType()).isEqualTo(CustomerAddressTypeEnum.RESIDENTIAL);
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    CustomerAddressDto customerAddressDto = new CustomerAddressDto();

    customerAddressDto.setId(1L);
    customerAddressDto.setStreet("123 Main St");
    customerAddressDto.setCity("City");
    customerAddressDto.setState("State");
    customerAddressDto.setZipCode("1234567890");
    customerAddressDto.setIsDefault(true);
    customerAddressDto.setAddressType(CustomerAddressTypeEnum.RESIDENTIAL);

    // Assert
    assertThat(customerAddressDto.getId()).isEqualTo(1L);
    assertThat(customerAddressDto.getStreet()).isEqualTo("123 Main St");
    assertThat(customerAddressDto.getCity()).isEqualTo("City");
    assertThat(customerAddressDto.getState()).isEqualTo("State");
    assertThat(customerAddressDto.getZipCode()).isEqualTo("1234567890");
    assertThat(customerAddressDto.getIsDefault()).isEqualTo(true);
    assertThat(customerAddressDto.getAddressType()).isEqualTo(CustomerAddressTypeEnum.RESIDENTIAL);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    CustomerAddressDto customerAddressDto = new CustomerAddressDto();

    // Assert
    assertThat(customerAddressDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    CustomerAddressDto customerAddressDto = new CustomerAddressDto(
        1L, "123 Main St", "City", "State", "1234567890",
        true, CustomerAddressTypeEnum.RESIDENTIAL);

    // Assert
    assertThat(customerAddressDto).isNotNull();
    assertThat(customerAddressDto.getId()).isEqualTo(1L);
    assertThat(customerAddressDto.getStreet()).isEqualTo("123 Main St");
    assertThat(customerAddressDto.getCity()).isEqualTo("City");
    assertThat(customerAddressDto.getState()).isEqualTo("State");
    assertThat(customerAddressDto.getZipCode()).isEqualTo("1234567890");
    assertThat(customerAddressDto.getIsDefault()).isEqualTo(true);
    assertThat(customerAddressDto.getAddressType()).isEqualTo(CustomerAddressTypeEnum.RESIDENTIAL);
  }
}
