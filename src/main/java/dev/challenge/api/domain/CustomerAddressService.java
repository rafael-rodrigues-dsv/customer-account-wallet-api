package dev.challenge.api.domain;

import dev.challenge.api.domain.model.CustomerAddressModel;

public interface CustomerAddressService {
  CustomerAddressModel add(Long customerId, CustomerAddressModel customerAddress);
  CustomerAddressModel update(Long customerAddressId, CustomerAddressModel customerAddress);
  CustomerAddressModel getById(Long customerAddressId);
}
