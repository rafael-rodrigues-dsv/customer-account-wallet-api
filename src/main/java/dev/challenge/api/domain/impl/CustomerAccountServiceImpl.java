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

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerAccountServiceImpl implements CustomerAccountService {

  private final CustomerAccountRepository customerAccountRepository;
  private final CustomerServiceImpl customerService;

  @Override
  public CustomerAccountModel add(Long customerId, CustomerAccountModel customerAddress) {
    customerAddress.setCustomer(customerService.findById(customerId));

    if (hasDefaultByCustomerId(customerId))
      customerAddress.setIsDefault(Boolean.FALSE);

    return customerAccountRepository.save(customerAddress);
  }

  @Override
  public CustomerAccountModel update(Long id, Long customerId, CustomerAccountModel customerBankAccount) {
    CustomerAccountModel existingCustomerBankAccount = findByIdAndCustomerId(id, customerId);
    existingCustomerBankAccount.setBalance(customerBankAccount.getBalance());
    return customerAccountRepository.save(existingCustomerBankAccount);
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
