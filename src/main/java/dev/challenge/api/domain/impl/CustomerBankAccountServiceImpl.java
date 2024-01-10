package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerBankAccountRepository;
import dev.challenge.api.domain.CustomerBankAccountService;
import dev.challenge.api.domain.model.CustomerBankAccountModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerBankAccountServiceImpl implements CustomerBankAccountService {

  private final CustomerBankAccountRepository customerBankAccountRepository;
  private final CustomerServiceImpl customerService;

  @Override
  public CustomerBankAccountModel add(Long customerId, CustomerBankAccountModel customerAddress) {
    customerAddress.setCustomer(customerService.getById(customerId));
    return customerBankAccountRepository.save(customerAddress);
  }

  @Override
  public CustomerBankAccountModel update(Long customerAddressId, CustomerBankAccountModel customerBankAccount) {
    return customerBankAccountRepository.findById(customerAddressId).map(existingBankAccount -> {
          existingBankAccount.setBalance(customerBankAccount.getBalance());
          return customerBankAccountRepository.save(existingBankAccount);
        })
        .orElseThrow(() -> new RuntimeException("CustomerBankAccount not found with id " + customerAddressId));
  }

  @Override
  public CustomerBankAccountModel getById(Long customerBankAccountId) {
    return customerBankAccountRepository.findById(customerBankAccountId)
        .orElseThrow(() -> new RuntimeException("CustomerBankAccount not found with id " + customerBankAccountId));
  }
}
