package dev.challenge.api.domain;

import dev.challenge.api.domain.model.CustomerBankAccountModel;

public interface CustomerBankAccountService {
  CustomerBankAccountModel add(Long customerId, CustomerBankAccountModel customerBankAccount);
  CustomerBankAccountModel update(Long customerBankAccountId, CustomerBankAccountModel customerBankAccount);
  CustomerBankAccountModel getById(Long customerBankAccountId);
}
