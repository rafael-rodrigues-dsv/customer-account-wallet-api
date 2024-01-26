package dev.challenge.api.adapter.entrypoint.dto.customeraddress;

import dev.challenge.api.domain.enumeration.CustomerAddressTypeEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateCustomerAddressDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidUpdateCustomerAddressDto() {
    // Arrange
    UpdateCustomerAddressDto updateCustomerAddressDto = UpdateCustomerAddressDto.builder()
        .id(1L)
        .customerId(1L)
        .street("123 Main St")
        .city("City")
        .state("State")
        .zipCode("1234567890")
        .addressType(CustomerAddressTypeEnum.RESIDENTIAL)
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerAddressDto>> violations = validator.validate(updateCustomerAddressDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidUpdateCustomerAddressDto() {
    // Arrange
    UpdateCustomerAddressDto updateCustomerAddressDto = UpdateCustomerAddressDto.builder()
        .id(null)
        .customerId(null)
        .street("") // Blank street
        .city("City")
        .state("State")
        .zipCode("invalid-zip") // Invalid zip code
        .addressType(null) // Null address type
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerAddressDto>> violations = validator.validate(updateCustomerAddressDto);

    // Assert
    assertThat(violations).hasSize(4); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    UpdateCustomerAddressDto updateCustomerAddressDto = new UpdateCustomerAddressDto();

    // Act
    updateCustomerAddressDto.setId(1L);
    updateCustomerAddressDto.setCustomerId(1L);
    updateCustomerAddressDto.setStreet("123 Main St");
    updateCustomerAddressDto.setCity("City");
    updateCustomerAddressDto.setState("State");
    updateCustomerAddressDto.setZipCode("1234567890");
    updateCustomerAddressDto.setAddressType(CustomerAddressTypeEnum.RESIDENTIAL);

    // Assert
    assertThat(updateCustomerAddressDto.getId()).isEqualTo(1L);
    assertThat(updateCustomerAddressDto.getCustomerId()).isEqualTo(1L);
    assertThat(updateCustomerAddressDto.getStreet()).isEqualTo("123 Main St");
    assertThat(updateCustomerAddressDto.getCity()).isEqualTo("City");
    assertThat(updateCustomerAddressDto.getState()).isEqualTo("State");
    assertThat(updateCustomerAddressDto.getZipCode()).isEqualTo("1234567890");
    assertThat(updateCustomerAddressDto.getAddressType()).isEqualTo(CustomerAddressTypeEnum.RESIDENTIAL);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    UpdateCustomerAddressDto updateCustomerAddressDto = new UpdateCustomerAddressDto();

    // Assert
    assertThat(updateCustomerAddressDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    UpdateCustomerAddressDto updateCustomerAddressDto = new UpdateCustomerAddressDto(
        1L,
        1L,
        "123 Main St",
        "City",
        "State",
        "1234567890",
        CustomerAddressTypeEnum.RESIDENTIAL
    );

    // Assert
    assertThat(updateCustomerAddressDto).isNotNull();
    assertThat(updateCustomerAddressDto.getId()).isEqualTo(1L);
    assertThat(updateCustomerAddressDto.getCustomerId()).isEqualTo(1L);
    assertThat(updateCustomerAddressDto.getStreet()).isEqualTo("123 Main St");
    assertThat(updateCustomerAddressDto.getCity()).isEqualTo("City");
    assertThat(updateCustomerAddressDto.getState()).isEqualTo("State");
    assertThat(updateCustomerAddressDto.getZipCode()).isEqualTo("1234567890");
    assertThat(updateCustomerAddressDto.getAddressType()).isEqualTo(CustomerAddressTypeEnum.RESIDENTIAL);
  }
}
