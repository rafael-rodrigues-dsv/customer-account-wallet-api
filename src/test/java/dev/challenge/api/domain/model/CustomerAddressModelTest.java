package dev.challenge.api.domain.model;

import dev.challenge.api.domain.enumeration.CustomerAddressTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerAddressModelTest {

  @Test
  void testBuilder() {
    CustomerModel customer = CustomerModel.builder().id(1L).build();

    CustomerAddressModel address = CustomerAddressModel.builder()
        .id(1L)
        .customer(customer)
        .street("123 Main St")
        .city("City")
        .state("State")
        .zipCode("12345")
        .isDefault(true)
        .addressType(CustomerAddressTypeEnum.RESIDENTIAL)
        .build();

    assertNotNull(address);
    assertAddressFields(address);
  }

  @Test
  void testGettersAndSetters() {
    CustomerModel customer = CustomerModel.builder().id(1L).build();

    CustomerAddressModel address = new CustomerAddressModel();
    address.setId(1L);
    address.setCustomer(customer);
    address.setStreet("123 Main St");
    address.setCity("City");
    address.setState("State");
    address.setZipCode("12345");
    address.setIsDefault(true);
    address.setAddressType(CustomerAddressTypeEnum.RESIDENTIAL);

    assertAddressFields(address);
  }

  @Test
  void testNoArgsConstructor() {
    CustomerAddressModel address = new CustomerAddressModel();
    assertNotNull(address);
  }

  private void assertAddressFields(CustomerAddressModel address) {
    assertEquals(1L, address.getId());
    assertNotNull(address.getCustomer());
    assertEquals("123 Main St", address.getStreet());
    assertEquals("City", address.getCity());
    assertEquals("State", address.getState());
    assertEquals("12345", address.getZipCode());
    assertTrue(address.getIsDefault());
    assertEquals(CustomerAddressTypeEnum.RESIDENTIAL, address.getAddressType());
  }
}
