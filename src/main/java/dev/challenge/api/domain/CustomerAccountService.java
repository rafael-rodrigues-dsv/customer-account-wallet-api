package dev.challenge.api.domain;

import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import dev.challenge.api.domain.model.CustomerAccountModel;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerAccountService {
  CustomerAccountModel add(Long customerId, CustomerAccountModel customerAccount);
  CustomerAccountModel update(Long id, Long customerId, CustomerAccountModel customerAccount);
  CustomerAccountModel updateBalance(Long id, Long customerId, BigDecimal balance);
  CustomerAccountModel updateAccountStatus(Long id, Long customerId, CustomerAccountStatusEnum accountStatus);
  CustomerAccountModel findById(Long id);
  CustomerAccountModel findByIdAndVerifyCustomerId(Long id, Long customerId);
  List<CustomerAccountModel> findAllByCustomerId(Long customerId);
}
