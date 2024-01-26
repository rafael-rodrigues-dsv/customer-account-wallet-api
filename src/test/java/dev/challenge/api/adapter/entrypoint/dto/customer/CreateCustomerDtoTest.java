package dev.challenge.api.adapter.entrypoint.dto.customer;

import dev.challenge.api.domain.enumeration.CustomerTypeEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCustomerDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidCreateCustomerDto() {
    // Arrange
    CreateCustomerDto createCustomerDto = CreateCustomerDto.builder()
        .customerType(CustomerTypeEnum.NATURAL_PERSON)
        .documentNumber("12345678901")
        .name("John Doe")
        .email("john.doe@example.com")
        .phoneNumber("12345678901")
        .password("Password123!")
        .build();

    // Act
    Set<ConstraintViolation<CreateCustomerDto>> violations = validator.validate(createCustomerDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidCreateCustomerDto() {
    // Arrange
    CreateCustomerDto createCustomerDto = CreateCustomerDto.builder()
        .customerType(null)
        .documentNumber("12345678901234567890") // Invalid length
        .name("") // Blank name
        .email("invalid-email") // Invalid email format
        .phoneNumber("123") // Invalid length
        .password("invalid_password") // Invalid password format
        .build();

    // Act
    Set<ConstraintViolation<CreateCustomerDto>> violations = validator.validate(createCustomerDto);

    // Assert
    assertThat(violations).hasSize(8); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    CreateCustomerDto createCustomerDto = new CreateCustomerDto();

    // Act
    createCustomerDto.setCustomerType(CustomerTypeEnum.NATURAL_PERSON);
    createCustomerDto.setDocumentNumber("12345678901");
    createCustomerDto.setName("John Doe");
    createCustomerDto.setEmail("john.doe@example.com");
    createCustomerDto.setPhoneNumber("12345678901");
    createCustomerDto.setPassword("Password123!");

    // Assert
    assertThat(createCustomerDto.getCustomerType()).isEqualTo(CustomerTypeEnum.NATURAL_PERSON);
    assertThat(createCustomerDto.getDocumentNumber()).isEqualTo("12345678901");
    assertThat(createCustomerDto.getName()).isEqualTo("John Doe");
    assertThat(createCustomerDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(createCustomerDto.getPhoneNumber()).isEqualTo("12345678901");
    assertThat(createCustomerDto.getPassword()).isEqualTo("Password123!");
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    CreateCustomerDto createCustomerDto = new CreateCustomerDto();

    // Assert
    assertThat(createCustomerDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    CreateCustomerDto createCustomerDto = new CreateCustomerDto(
        CustomerTypeEnum.NATURAL_PERSON,
        "12345678901",
        "John Doe",
        "john.doe@example.com",
        "12345678901",
        "Password123!"
    );

    // Assert
    assertThat(createCustomerDto).isNotNull();
    assertThat(createCustomerDto.getCustomerType()).isEqualTo(CustomerTypeEnum.NATURAL_PERSON);
    assertThat(createCustomerDto.getDocumentNumber()).isEqualTo("12345678901");
    assertThat(createCustomerDto.getName()).isEqualTo("John Doe");
    assertThat(createCustomerDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(createCustomerDto.getPhoneNumber()).isEqualTo("12345678901");
    assertThat(createCustomerDto.getPassword()).isEqualTo("Password123!");
  }
}
