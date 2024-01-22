package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerAccountRepository;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.model.CustomerAccountModel;
import dev.challenge.api.domain.model.CustomerAddressModel;
import dev.challenge.api.domain.model.CustomerModel;
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
  private final CustomerServiceImpl customerService;

  @Override
  public CustomerAccountModel add(Long customerId, CustomerAccountModel customerAddress) {

    CustomerAccountModel newAccount = CustomerAccountModel.builder()
        .customer(customerService.findById(customerId))
        .agency(customerAddress.getAgency())
        .accountNumber(customerAddress.getAccountNumber())
        .balance(customerAddress.getBalance())
        .isDefault(hasDefaultByCustomerId(customerId) ? Boolean.FALSE : Boolean.TRUE)
        .build();

    return customerAccountRepository.save(newAccount);
  }

  @Override
  public CustomerAccountModel update(Long id, Long customerId, CustomerAccountModel customerBankAccount) {
    CustomerAccountModel existingAccount = findByIdAndCustomerId(id, customerId);
    existingAccount.setBalance(customerBankAccount.getBalance());
    return customerAccountRepository.save(existingAccount);
  }

  @Override
  public CustomerAccountModel findByIdAndCustomerId(Long id, Long customerId) {
    Example<CustomerAccountModel> filter = Example.of(CustomerAccountModel.builder()
        .id(id)
        .customer(CustomerModel.builder().id(customerId).build())
        .build());

    return customerAccountRepository
        .findAll(filter)
        .stream()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("CustomerBankAccount not found with CustomerId " + customerId + " and CustomerBankAccountId " + id));
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

  private boolean hasDefaultByCustomerId(Long customerId) {
    Example<CustomerAccountModel> filter = Example.of(CustomerAccountModel.builder()
        .customer(CustomerModel.builder().id(customerId).build())
        .isDefault(Boolean.TRUE)
        .build());

    return customerAccountRepository
        .findAll(filter)
        .stream()
        .count() > 0;
  }
}
