package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerAccountRepository;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.CustomerService;
import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import dev.challenge.api.domain.model.CustomerAccountModel;
import dev.challenge.api.domain.model.CustomerModel;
import dev.challenge.api.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerAccountServiceImpl implements CustomerAccountService {

  private final CustomerAccountRepository customerAccountRepository;
  private final CustomerService customerService;

  @Override
  public CustomerAccountModel add(Long customerId, CustomerAccountModel customerAccount) {
    CustomerAccountModel newAccount = CustomerAccountModel.builder()
        .customer(customerService.findById(customerId))
        .agency(customerAccount.getAgency())
        .accountNumber(customerAccount.getAccountNumber())
        .balance(customerAccount.getBalance())
        .isDefault(hasDefaultByCustomerId(customerId) ? Boolean.FALSE : Boolean.TRUE)
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .build();

    return customerAccountRepository.save(newAccount);
  }

  @Override
  public CustomerAccountModel update(Long id, CustomerAccountModel customerAccount) {
    CustomerAccountModel existingAccount = findById(id);
    existingAccount.setAgency(customerAccount.getAgency());
    existingAccount.setAccountNumber(customerAccount.getAccountNumber());
    return customerAccountRepository.save(existingAccount);
  }

  @Override
  public CustomerAccountModel updateBalance(Long id, BigDecimal balance) {
    return customerAccountRepository.findById(id).map(existingAccount -> {
      CustomerAccountStatusEnum accountStatus = existingAccount.getAccountStatus();

      if (!accountStatus.equals(CustomerAccountStatusEnum.ACTIVE)) {
        if (accountStatus.equals(CustomerAccountStatusEnum.BLOCKED)) {
          throw new BusinessException("Balance cannot be updated because Account is blocked with id " + id);
        } else {
          throw new BusinessException("Balance cannot be updated because Account is disabled with id " + id);
        }
      }

      existingAccount.setBalance(balance);
      return customerAccountRepository.save(existingAccount);
    }).orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));
  }

  @Override
  public CustomerAccountModel updateAccountStatus(Long id, CustomerAccountStatusEnum accountStatus) {
    return customerAccountRepository.findById(id).map(existingAccount -> {
      existingAccount.setAccountStatus(accountStatus);
      return customerAccountRepository.save(existingAccount);
    }).orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));
  }

  @Override
  public CustomerAccountModel findById(Long id) {
    return customerAccountRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer Account not found with id " + id));
  }

  @Override
  public CustomerAccountModel findByIdAndCustomerId(Long id, Long customerId) {
    Example<CustomerAccountModel> filter = Example.of(CustomerAccountModel.builder()
        .id(id)
        .customer(CustomerModel.builder().id(customerId).build())
        .build());

    return customerAccountRepository.findAll(filter)
        .stream()
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException("Account not found with CustomerId " + customerId + " and CustomerBankAccountId " + id));
  }

  @Override
  public List<CustomerAccountModel> findAllByCustomerId(Long customerId) {
    Example<CustomerAccountModel> filter = Example.of(CustomerAccountModel.builder()
        .customer(CustomerModel.builder().id(customerId).build())
        .build());

    return customerAccountRepository.findAll(filter)
        .stream()
        .toList();
  }

  private Boolean hasDefaultByCustomerId(Long customerId) {
    Example<CustomerAccountModel> filter = Example.of(CustomerAccountModel.builder()
        .customer(CustomerModel.builder().id(customerId).build())
        .isDefault(Boolean.TRUE)
        .build());

    return customerAccountRepository.exists(filter);
  }
}
