package dev.challenge.api.domain.model;

import dev.challenge.api.domain.enumeration.CustomerTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerModelTest {


  @Test
  void testBuilder() {
    List<CustomerAccountModel> accounts = List.of(CustomerAccountModel.builder().build());
    List<CustomerAddressModel> addresses = List.of(CustomerAddressModel.builder().build());

    CustomerModel customer = CustomerModel.builder()
        .id(1L)
        .customerType(CustomerTypeEnum.NATURAL_PERSON)
        .documentNumber("123456789")
        .name("John Doe")
        .email("john.doe@example.com")
        .phoneNumber("123-456-7890")
        .password("password")
        .customerAccounts(Set.copyOf(accounts))
        .customerAddresses(Set.copyOf(addresses))
        .build();

    assertNotNull(customer);
    assertCustomerFields(customer);
  }

  @Test
  void testGettersAndSetters() {
    CustomerModel customer = new CustomerModel();
    customer.setId(1L);
    customer.setCustomerType(CustomerTypeEnum.NATURAL_PERSON);
    customer.setDocumentNumber("123456789");
    customer.setName("John Doe");
    customer.setEmail("john.doe@example.com");
    customer.setPhoneNumber("123-456-7890");
    customer.setPassword("password");
    customer.setCustomerAccounts(Set.of(CustomerAccountModel.builder().build()));
    customer.setCustomerAddresses(Set.of(CustomerAddressModel.builder().build()));
    assertCustomerFields(customer);
  }

  private void assertCustomerFields(CustomerModel customer) {
    assertEquals(1L, customer.getId());
    assertEquals(CustomerTypeEnum.NATURAL_PERSON, customer.getCustomerType());
    assertEquals("123456789", customer.getDocumentNumber());
    assertEquals("John Doe", customer.getName());
    assertEquals("john.doe@example.com", customer.getEmail());
    assertEquals("123-456-7890", customer.getPhoneNumber());
    assertEquals("password", customer.getPassword());
    assertNotNull(customer.getCustomerAccounts());
    assertNotNull(customer.getCustomerAddresses());
  }
}
