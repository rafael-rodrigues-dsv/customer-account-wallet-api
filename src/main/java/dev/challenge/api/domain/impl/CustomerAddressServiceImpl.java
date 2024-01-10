package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerAddressRepository;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerAddressServiceImpl implements CustomerAddressService {

  private final CustomerAddressRepository customerAddressRepository;
  private final CustomerServiceImpl customerService;

  @Override
  public CustomerAddressModel add(Long customerId, CustomerAddressModel customerAddress) {
    customerAddress.setCustomer(customerService.getById(customerId));
    return customerAddressRepository.save(customerAddress);
  }

  @Override
  public CustomerAddressModel update(Long customerAddressId, CustomerAddressModel address) {
    return customerAddressRepository.findById(customerAddressId).map(existingAddress -> {
          existingAddress.setAddressType(address.getAddressType());
          existingAddress.setStreet(address.getStreet());
          existingAddress.setCity(address.getCity());
          existingAddress.setState(address.getState());
          existingAddress.setZipCode(address.getZipCode());
          return customerAddressRepository.save(existingAddress);
        })
        .orElseThrow(() -> new RuntimeException("CustomerAddress not found with id " + customerAddressId));
  }

  @Override
  public CustomerAddressModel getById(Long customerAddressId) {
    return customerAddressRepository.findById(customerAddressId)
        .orElseThrow(() -> new RuntimeException("CustomerAddress not found with id " + customerAddressId));
  }
}
