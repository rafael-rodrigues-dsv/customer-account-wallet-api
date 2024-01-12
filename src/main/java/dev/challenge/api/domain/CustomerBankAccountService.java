package dev.challenge.api.domain;

import dev.challenge.api.domain.model.CustomerBankAccountModel;

import java.util.List;

public interface CustomerBankAccountService {
  CustomerBankAccountModel add(Long customerId, CustomerBankAccountModel customerBankAccount);
  CustomerBankAccountModel update(Long id, Long customerId, CustomerBankAccountModel customerBankAccount);
  CustomerBankAccountModel findByIdAndCustomerId(Long id, Long customerId);
  List<CustomerBankAccountModel> findAllByCustomerId(Long customerId);
}
