package dev.challenge.api.domain;

import dev.challenge.api.domain.model.CustomerAccountModel;

import java.util.List;

public interface CustomerAccountService {
  CustomerAccountModel add(Long customerId, CustomerAccountModel customerBankAccount);
  CustomerAccountModel update(Long id, Long customerId, CustomerAccountModel customerBankAccount);
  CustomerAccountModel findByIdAndCustomerId(Long id, Long customerId);
  List<CustomerAccountModel> findAllByCustomerId(Long customerId);
}
