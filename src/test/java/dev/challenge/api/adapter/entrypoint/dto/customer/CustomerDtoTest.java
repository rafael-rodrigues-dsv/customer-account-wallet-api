package dev.challenge.api.adapter.entrypoint.dto.customer;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.domain.enumeration.CustomerTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerDtoTest {

  @Test
  void testBuilder() {
    // Arrange
    Set<CustomerAddressDto> customerAddresses = new HashSet<>();
    Set<CustomerAccountDto> customerAccounts = new HashSet<>();

    CustomerDto customerDto = CustomerDto.builder()
        .id(1L)
        .customerType(CustomerTypeEnum.NATURAL_PERSON)
        .documentNumber("12345678901")
        .name("John Doe")
        .email("john.doe@example.com")
        .phoneNumber("12345678901")
        .customerAddresses(customerAddresses)
        .customerAccounts(customerAccounts)
        .build();

    // Assert
    assertThat(customerDto.getId()).isEqualTo(1L);
    assertThat(customerDto.getCustomerType()).isEqualTo(CustomerTypeEnum.NATURAL_PERSON);
    assertThat(customerDto.getDocumentNumber()).isEqualTo("12345678901");
    assertThat(customerDto.getName()).isEqualTo("John Doe");
    assertThat(customerDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(customerDto.getPhoneNumber()).isEqualTo("12345678901");
    assertThat(customerDto.getCustomerAddresses()).isEqualTo(customerAddresses);
    assertThat(customerDto.getCustomerAccounts()).isEqualTo(customerAccounts);
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    Set<CustomerAddressDto> customerAddresses = new HashSet<>();
    Set<CustomerAccountDto> customerAccounts = new HashSet<>();

    CustomerDto customerDto = new CustomerDto();

    customerDto.setId(1L);
    customerDto.setCustomerType(CustomerTypeEnum.NATURAL_PERSON);
    customerDto.setDocumentNumber("12345678901");
    customerDto.setName("John Doe");
    customerDto.setEmail("john.doe@example.com");
    customerDto.setPhoneNumber("12345678901");
    customerDto.setCustomerAddresses(customerAddresses);
    customerDto.setCustomerAccounts(customerAccounts);

    // Assert
    assertThat(customerDto.getId()).isEqualTo(1L);
    assertThat(customerDto.getCustomerType()).isEqualTo(CustomerTypeEnum.NATURAL_PERSON);
    assertThat(customerDto.getDocumentNumber()).isEqualTo("12345678901");
    assertThat(customerDto.getName()).isEqualTo("John Doe");
    assertThat(customerDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(customerDto.getPhoneNumber()).isEqualTo("12345678901");
    assertThat(customerDto.getCustomerAddresses()).isEqualTo(customerAddresses);
    assertThat(customerDto.getCustomerAccounts()).isEqualTo(customerAccounts);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    CustomerDto customerDto = new CustomerDto();

    // Assert
    assertThat(customerDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Set<CustomerAddressDto> customerAddresses = new HashSet<>();
    Set<CustomerAccountDto> customerAccounts = new HashSet<>();

    CustomerDto customerDto = new CustomerDto(
        1L, CustomerTypeEnum.NATURAL_PERSON, "12345678901",
        "John Doe", "john.doe@example.com", "12345678901",
        customerAddresses, customerAccounts);

    // Assert
    assertThat(customerDto).isNotNull();
    assertThat(customerDto.getId()).isEqualTo(1L);
    assertThat(customerDto.getCustomerType()).isEqualTo(CustomerTypeEnum.NATURAL_PERSON);
    assertThat(customerDto.getDocumentNumber()).isEqualTo("12345678901");
    assertThat(customerDto.getName()).isEqualTo("John Doe");
    assertThat(customerDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(customerDto.getPhoneNumber()).isEqualTo("12345678901");
    assertThat(customerDto.getCustomerAddresses()).isEqualTo(customerAddresses);
    assertThat(customerDto.getCustomerAccounts()).isEqualTo(customerAccounts);
  }
}
