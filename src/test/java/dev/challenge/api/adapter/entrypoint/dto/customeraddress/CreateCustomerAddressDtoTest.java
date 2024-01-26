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

class CreateCustomerAddressDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidCreateCustomerAddressDto() {
    // Arrange
    CreateCustomerAddressDto createCustomerAddressDto = CreateCustomerAddressDto.builder()
        .customerId(1L)
        .street("123 Main St")
        .city("Cityville")
        .state("State")
        .zipCode("1234567890")
        .addressType(CustomerAddressTypeEnum.RESIDENTIAL)
        .build();

    // Act
    Set<ConstraintViolation<CreateCustomerAddressDto>> violations = validator.validate(createCustomerAddressDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidCreateCustomerAddressDto() {
    // Arrange
    CreateCustomerAddressDto createCustomerAddressDto = CreateCustomerAddressDto.builder()
        .customerId(null) // Null customerId
        .street("") // Blank street
        .city("CityWithALongNameThatExceedsTheMaximumAllowedLength") // Invalid city length
        .state("StateWithALongNameThatExceedsTheMaximumAllowedLength") // Invalid state length
        .zipCode("invalid_zip_code") // Invalid zip code format
        .addressType(null) // Null address type
        .build();

    // Act
    Set<ConstraintViolation<CreateCustomerAddressDto>> violations = validator.validate(createCustomerAddressDto);

    // Assert
    assertThat(violations).hasSize(5); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    CreateCustomerAddressDto createCustomerAddressDto = new CreateCustomerAddressDto();

    // Act
    createCustomerAddressDto.setCustomerId(1L);
    createCustomerAddressDto.setStreet("123 Main St");
    createCustomerAddressDto.setCity("Cityville");
    createCustomerAddressDto.setState("State");
    createCustomerAddressDto.setZipCode("1234567890");
    createCustomerAddressDto.setAddressType(CustomerAddressTypeEnum.RESIDENTIAL);

    // Assert
    assertThat(createCustomerAddressDto.getCustomerId()).isEqualTo(1L);
    assertThat(createCustomerAddressDto.getStreet()).isEqualTo("123 Main St");
    assertThat(createCustomerAddressDto.getCity()).isEqualTo("Cityville");
    assertThat(createCustomerAddressDto.getState()).isEqualTo("State");
    assertThat(createCustomerAddressDto.getZipCode()).isEqualTo("1234567890");
    assertThat(createCustomerAddressDto.getAddressType()).isEqualTo(CustomerAddressTypeEnum.RESIDENTIAL);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    CreateCustomerAddressDto createCustomerAddressDto = new CreateCustomerAddressDto();

    // Assert
    assertThat(createCustomerAddressDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    CreateCustomerAddressDto createCustomerAddressDto = new CreateCustomerAddressDto(
        1L,
        "123 Main St",
        "Cityville",
        "State",
        "1234567890",
        CustomerAddressTypeEnum.RESIDENTIAL
    );

    // Assert
    assertThat(createCustomerAddressDto).isNotNull();
    assertThat(createCustomerAddressDto.getCustomerId()).isEqualTo(1L);
    assertThat(createCustomerAddressDto.getStreet()).isEqualTo("123 Main St");
    assertThat(createCustomerAddressDto.getCity()).isEqualTo("Cityville");
    assertThat(createCustomerAddressDto.getState()).isEqualTo("State");
    assertThat(createCustomerAddressDto.getZipCode()).isEqualTo("1234567890");
    assertThat(createCustomerAddressDto.getAddressType()).isEqualTo(CustomerAddressTypeEnum.RESIDENTIAL);
  }
}
