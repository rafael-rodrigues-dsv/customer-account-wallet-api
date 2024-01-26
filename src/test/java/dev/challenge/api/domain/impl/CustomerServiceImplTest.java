package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerRepository;
import dev.challenge.api.domain.model.CustomerModel;
import dev.challenge.api.exception.DomainRuleException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerServiceImpl customerService;

  @Test
  void testAdd_Success() {
    CustomerModel customer = createCustomer();
    when(customerRepository.exists(any())).thenReturn(false);
    when(customerRepository.save(any())).thenReturn(customer);

    CustomerModel result = customerService.add(customer);

    assertNotNull(result);
    assertEquals(customer.getName(), result.getName());
    assertEquals(customer.getEmail(), result.getEmail());
    assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());
    verify(customerRepository, times(1)).save(any());
  }

  @Test
  void testAdd_Failure_DuplicateDocumentNumber() {
    CustomerModel customer = createCustomer();
    when(customerRepository.exists(any())).thenReturn(true);

    assertThrows(DomainRuleException.class, () -> customerService.add(customer));
    verify(customerRepository, never()).save(any());
  }

  @Test
  void testUpdate_Success() {
    Long customerId = 1L;
    CustomerModel existingCustomer = createCustomer();
    CustomerModel updatedCustomer = createCustomer();
    updatedCustomer.setName("UpdatedName");

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
    when(customerRepository.save(any())).thenReturn(updatedCustomer);

    CustomerModel result = customerService.update(customerId, updatedCustomer);

    assertNotNull(result);
    assertEquals(updatedCustomer.getName(), result.getName());
    verify(customerRepository, times(1)).save(any());
  }

  @Test
  void testUpdate_Failure_CustomerNotFound() {
    Long customerId = 1L;
    CustomerModel updatedCustomer = createCustomer();

    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> customerService.update(customerId, updatedCustomer));
    verify(customerRepository, never()).save(any());
  }

  @Test
  void testFindById_Success() {
    Long customerId = 1L;
    CustomerModel existingCustomer = createCustomer();

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

    CustomerModel result = customerService.findById(customerId);

    assertNotNull(result);
    assertEquals(existingCustomer.getName(), result.getName());
    verify(customerRepository, times(1)).findById(customerId);
  }

  @Test
  void testFindById_Failure_CustomerNotFound() {
    Long customerId = 1L;
    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> customerService.findById(customerId));
    verify(customerRepository, times(1)).findById(customerId);
  }

  @Test
  void testCheckPassword_Success() {
    CustomerModel existingCustomer = CustomerModel.builder()
        .id(1L)
        .password(new BCryptPasswordEncoder().encode("password"))
        .build();

    when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));

    boolean result = customerService.checkPassword(1L, "password");

    assertTrue(result);
  }

  @Test
  void testCheckPassword_Failure_CustomerNotFound() {
    Long customerId = 1L;
    String password = "password";

    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> customerService.checkPassword(customerId, password));
    verify(customerRepository, times(1)).findById(customerId);
  }

  @Test
  void testCheckPassword_Failure_PasswordMismatch() {
    Long customerId = 1L;
    CustomerModel existingCustomer = createCustomer();
    String password = "password";

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

    assertFalse(customerService.checkPassword(customerId, password));
    verify(customerRepository, times(1)).findById(customerId);
  }

  private CustomerModel createCustomer() {
    return CustomerModel.builder()
        .id(1L)
        .name("John Doe")
        .documentNumber("123456789")
        .email("john.doe@example.com")
        .phoneNumber("123-456-7890")
        .password("password")
        .build();
  }
}
