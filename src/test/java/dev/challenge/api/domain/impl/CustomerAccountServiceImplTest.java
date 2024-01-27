package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerAccountRepository;
import dev.challenge.api.domain.CustomerService;
import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import dev.challenge.api.domain.model.CustomerAccountModel;
import dev.challenge.api.domain.model.CustomerModel;
import dev.challenge.api.exception.DomainRuleException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAccountServiceImplTest {

  @Mock
  private CustomerAccountRepository customerAccountRepository;

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private CustomerAccountServiceImpl customerAccountService;

  private CustomerAccountModel UpdateCustomerAccount() {
    return CustomerAccountModel.builder()
        .id(1L)
        .customer(CustomerModel.builder().id(1L).build())
        .agency("12345")
        .accountNumber("67890")
        .balance(BigDecimal.ZERO)
        .isDefault(true)
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .build();
  }

  @Test
  void testAdd_Success() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    Long customerId = 1L;

    when(customerService.findById(customerId)).thenReturn(CustomerModel.builder().id(customerId).build());
    when(customerAccountRepository.exists(any())).thenReturn(false);
    when(customerAccountRepository.save(any())).thenReturn(customerAccount);

    // Act
    CustomerAccountModel result = customerAccountService.add(customerId, customerAccount);

    // Assert
    assertNotNull(result);
    assertEquals(customerAccount.getAgency(), result.getAgency());
    assertEquals(customerAccount.getAccountNumber(), result.getAccountNumber());
    assertEquals(customerAccount.getBalance(), result.getBalance());
    assertEquals(Boolean.TRUE, result.getIsDefault());
    assertEquals(customerAccount.getAccountStatus(), result.getAccountStatus());
    verify(customerAccountRepository, times(1)).save(any());
  }

  @Test
  void testAdd_Failure_DuplicateAccountNumber() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    Long customerId = 1L;

    // Use lenient() to make the stubbing lenient
    when(customerAccountRepository.exists(any())).thenReturn(true);

    // Act & Assert
    assertThrows(DomainRuleException.class, () -> customerAccountService.add(customerId, customerAccount));
    verify(customerAccountRepository, never()).save(any());
  }

  @Test
  void testUpdate_Success() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    Long accountId = customerAccount.getId();
    Long customerId = customerAccount.getCustomer().getId();

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.of(customerAccount));
    when(customerAccountRepository.save(any())).thenReturn(customerAccount);

    // Act
    CustomerAccountModel result = customerAccountService.update(accountId, customerId, customerAccount);

    // Assert
    assertNotNull(result);
    assertEquals(customerAccount.getAgency(), result.getAgency());
    assertEquals(customerAccount.getAccountNumber(), result.getAccountNumber());
    verify(customerAccountRepository, times(1)).save(any());
  }

  @Test
  void testUpdate_Failure_DuplicateAccountNumber() {
    // Arrange
    Long id = 1L;
    Long customerId = 1L;
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    CustomerAccountModel customerAccountToUpdate = CustomerAccountModel.builder()
        .id(1L)
        .customer(CustomerModel.builder().id(1L).build())
        .accountNumber("123")
        .build();

    when(customerAccountRepository.findById(id)).thenReturn(Optional.of(customerAccountToUpdate));
    when(customerAccountRepository.exists(any())).thenReturn(true);

    // Act & Assert
    assertThrows(DomainRuleException.class, () -> customerAccountService.update(id, customerId, customerAccount));
    verify(customerAccountRepository, never()).save(any());
  }

  @Test
  void testUpdate_Failure_AccountNotFound() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    Long accountId = customerAccount.getId();
    Long customerId = customerAccount.getCustomer().getId();

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> customerAccountService.update(accountId, customerId, customerAccount));
    verify(customerAccountRepository, never()).save(any());
  }

  @Test
  void testUpdateBalance_Success() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    Long accountId = customerAccount.getId();
    Long customerId = customerAccount.getCustomer().getId();
    BigDecimal newBalance = BigDecimal.TEN;

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.of(customerAccount));
    when(customerAccountRepository.save(any())).thenReturn(customerAccount);

    // Act
    CustomerAccountModel result = customerAccountService.updateBalance(accountId, customerId, newBalance);

    // Assert
    assertNotNull(result);
    assertEquals(newBalance, result.getBalance());
    verify(customerAccountRepository, times(1)).save(any());
  }

  @Test
  void testUpdateBalance_Failure_AccountNotFound() {
    // Arrange
    Long accountId = 1L;
    Long customerId = 1L;
    BigDecimal newBalance = BigDecimal.TEN;

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> customerAccountService.updateBalance(accountId, customerId, newBalance));
    verify(customerAccountRepository, never()).save(any());
  }

  @Test
  void testUpdateBalance_Failure_AccountBlocked() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    customerAccount.setAccountStatus(CustomerAccountStatusEnum.BLOCKED);
    Long accountId = customerAccount.getId();
    Long customerId = customerAccount.getCustomer().getId();
    BigDecimal newBalance = BigDecimal.TEN;

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.of(customerAccount));

    // Act & Assert
    assertThrows(DomainRuleException.class, () -> customerAccountService.updateBalance(accountId, customerId, newBalance));
    verify(customerAccountRepository, never()).save(any());
  }

  @Test
  void testUpdateBalance_Failure_AccountDisabled() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    customerAccount.setAccountStatus(CustomerAccountStatusEnum.DISABLED);
    Long accountId = customerAccount.getId();
    Long customerId = customerAccount.getCustomer().getId();
    BigDecimal newBalance = BigDecimal.TEN;

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.of(customerAccount));

    // Act & Assert
    assertThrows(DomainRuleException.class, () -> customerAccountService.updateBalance(accountId, customerId, newBalance));
    verify(customerAccountRepository, never()).save(any());
  }

  @Test
  void testUpdateAccountStatus_Success() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    Long accountId = customerAccount.getId();
    Long customerId = customerAccount.getCustomer().getId();
    CustomerAccountStatusEnum newStatus = CustomerAccountStatusEnum.BLOCKED;

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.of(customerAccount));
    when(customerAccountRepository.save(any())).thenReturn(customerAccount);

    // Act
    CustomerAccountModel result = customerAccountService.updateAccountStatus(accountId, customerId, newStatus);

    // Assert
    assertNotNull(result);
    assertEquals(newStatus, result.getAccountStatus());
    verify(customerAccountRepository, times(1)).save(any());
  }

  @Test
  void testUpdateAccountStatus_Failure_AccountNotFound() {
    // Arrange
    Long accountId = 1L;
    Long customerId = 1L;
    CustomerAccountStatusEnum newStatus = CustomerAccountStatusEnum.BLOCKED;

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> customerAccountService.updateAccountStatus(accountId, customerId, newStatus));
    verify(customerAccountRepository, never()).save(any());
  }

  @Test
  void testFindById_Success() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    Long accountId = customerAccount.getId();

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.of(customerAccount));

    // Act
    CustomerAccountModel result = customerAccountService.findById(accountId);

    // Assert
    assertNotNull(result);
    assertEquals(accountId, result.getId());
    verify(customerAccountRepository, times(1)).findById(accountId);
  }

  @Test
  void testFindById_Failure_AccountNotFound() {
    // Arrange
    Long accountId = 1L;

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> customerAccountService.findById(accountId));
    verify(customerAccountRepository, times(1)).findById(accountId);
  }

  @Test
  void testFindByIdAndVerifyCustomerId_Success() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    Long accountId = customerAccount.getId();
    Long customerId = customerAccount.getCustomer().getId();

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.of(customerAccount));

    // Act
    CustomerAccountModel result = customerAccountService.findByIdAndVerifyCustomerId(accountId, customerId);

    // Assert
    assertNotNull(result);
    assertEquals(accountId, result.getId());
    verify(customerAccountRepository, times(1)).findById(accountId);
  }

  @Test
  void testFindByIdAndVerifyCustomerId_Failure_AccountNotFound() {
    // Arrange
    Long accountId = 1L;
    Long customerId = 1L;

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> customerAccountService.findByIdAndVerifyCustomerId(accountId, customerId));
    verify(customerAccountRepository, times(1)).findById(accountId);
  }

  @Test
  void testFindByIdAndVerifyCustomerId_Failure_WrongCustomerId() {
    // Arrange
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    Long accountId = customerAccount.getId();
    Long correctCustomerId = customerAccount.getCustomer().getId();
    Long wrongCustomerId = 99L;

    when(customerAccountRepository.findById(accountId)).thenReturn(Optional.of(customerAccount));

    // Act & Assert
    assertThrows(DomainRuleException.class, () -> customerAccountService.findByIdAndVerifyCustomerId(accountId, wrongCustomerId));
    verify(customerAccountRepository, times(1)).findById(accountId);
  }

  @Test
  void testFindAllByCustomerId_Success() {
    // Arrange
    Long customerId = 1L;
    CustomerAccountModel customerAccount = UpdateCustomerAccount();
    List<CustomerAccountModel> accounts = Collections.singletonList(customerAccount);

    Example<CustomerAccountModel> example = Example.of(
        CustomerAccountModel.builder()
            .customer(CustomerModel.builder().id(customerId).build())
            .build()
    );

    when(customerAccountRepository.findAll(any(Example.class))).thenReturn(accounts);

    // Act
    List<CustomerAccountModel> result = customerAccountService.findAllByCustomerId(customerId);

    // Assert
    assertNotNull(result);
    assertEquals(accounts.size(), result.size());
  }
}
