package dev.challenge.api.domain;

import dev.challenge.api.domain.model.CustomerAccountModel;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerAccountService {
  CustomerAccountModel add(Long customerId, CustomerAccountModel customerAccount);
  CustomerAccountModel update(Long id, CustomerAccountModel customerAccount);
  CustomerAccountModel updateBalance(Long id, BigDecimal balance);
  CustomerAccountModel findById(Long id);
  CustomerAccountModel findByIdAndCustomerId(Long id, Long customerId);
  List<CustomerAccountModel> findAllByCustomerId(Long customerId);
}
