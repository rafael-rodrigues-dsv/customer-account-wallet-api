package dev.challenge.api.domain;

import dev.challenge.api.domain.model.CustomerAddressModel;

import java.util.List;

public interface CustomerAddressService {
  CustomerAddressModel add(Long customerId, CustomerAddressModel customerAddress);
  CustomerAddressModel update(Long id, CustomerAddressModel customerAddress);
  CustomerAddressModel findById(Long id);
  List<CustomerAddressModel> findAllByCustomerId(Long customerId);
}
