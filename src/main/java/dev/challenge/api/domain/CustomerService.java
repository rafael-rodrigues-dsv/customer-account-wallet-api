package dev.challenge.api.domain;

import dev.challenge.api.domain.model.CustomerModel;

public interface CustomerService {
  CustomerModel add(CustomerModel customer);
  CustomerModel update(Long id, CustomerModel customer);
  CustomerModel findById(Long id);
}
