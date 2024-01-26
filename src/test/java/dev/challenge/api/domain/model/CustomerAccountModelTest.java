package dev.challenge.api.domain.model;

import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerAccountModelTest {

  @Test
  void testBuilder() {
    CustomerModel customer = CustomerModel.builder().id(1L).build();

    CustomerAccountModel account = CustomerAccountModel.builder()
        .id(1L)
        .customer(customer)
        .agency("123")
        .accountNumber("789")
        .balance(BigDecimal.TEN)
        .isDefault(true)
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .build();

    assertNotNull(account);
    assertAccountFields(account);
  }

  @Test
  void testGettersAndSetters() {
    CustomerModel customer = CustomerModel.builder().id(1L).build();

    CustomerAccountModel account = new CustomerAccountModel();
    account.setId(1L);
    account.setCustomer(customer);
    account.setAgency("123");
    account.setAccountNumber("789");
    account.setBalance(BigDecimal.TEN);
    account.setIsDefault(true);
    account.setAccountStatus(CustomerAccountStatusEnum.ACTIVE);

    assertAccountFields(account);
  }

  @Test
  void testNoArgsConstructor() {
    CustomerAccountModel account = new CustomerAccountModel();
    assertNotNull(account);
  }

  private void assertAccountFields(CustomerAccountModel account) {
    assertEquals(1L, account.getId());
    assertNotNull(account.getCustomer());
    assertEquals("123", account.getAgency());
    assertEquals("789", account.getAccountNumber());
    assertEquals(BigDecimal.TEN, account.getBalance());
    assertTrue(account.getIsDefault());
    assertEquals(CustomerAccountStatusEnum.ACTIVE, account.getAccountStatus());
  }
}
