package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerBankAccountRepository;
import dev.challenge.api.domain.CustomerBankAccountService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import dev.challenge.api.domain.model.CustomerBankAccountModel;
import dev.challenge.api.domain.model.CustomerModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerBankAccountServiceImpl implements CustomerBankAccountService {

  private final CustomerBankAccountRepository customerBankAccountRepository;
  private final CustomerServiceImpl customerService;

  @Override
  public CustomerBankAccountModel add(Long customerId, CustomerBankAccountModel customerAddress) {
    customerAddress.setCustomer(customerService.findById(customerId));
    return customerBankAccountRepository.save(customerAddress);
  }

  @Override
  public CustomerBankAccountModel update(Long id, Long customerId, CustomerBankAccountModel customerBankAccount) {
    CustomerBankAccountModel existingCustomerBankAccount = findByIdAndCustomerId(id, customerId);
    existingCustomerBankAccount.setBalance(customerBankAccount.getBalance());
    return customerBankAccountRepository.save(existingCustomerBankAccount);
  }

  @Override
  public CustomerBankAccountModel findByIdAndCustomerId(Long id, Long customerId) {
    Example<CustomerBankAccountModel> filter = Example.of(CustomerBankAccountModel.builder()
        .id(id)
        .customer(CustomerModel.builder().id(customerId).build())
        .build());

    return customerBankAccountRepository
        .findAll(filter)
        .stream()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("CustomerBankAccount not found with CustomerId " + customerId + " and CustomerBankAccountId " + id));
  }

  @Override
  public List<CustomerBankAccountModel> findAllByCustomerId(Long customerId) {
    Example<CustomerBankAccountModel> filter = Example.of(CustomerBankAccountModel.builder()
        .customer(CustomerModel.builder().id(customerId).build())
        .build());

    return customerBankAccountRepository.findAll(filter)
        .stream()
        .toList();
  }
}
