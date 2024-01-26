package dev.challenge.api.domain;

import dev.challenge.api.domain.model.CustomerAddressModel;

import java.util.List;

public interface CustomerAddressService {
  CustomerAddressModel add(Long customerId, CustomerAddressModel customerAddress);
  CustomerAddressModel update(Long id, Long customerId, CustomerAddressModel customerAddress);
  CustomerAddressModel findByIdAndVerifyCustomerId(Long id, Long customerId);
  List<CustomerAddressModel> findAllByCustomerId(Long customerId);
}
