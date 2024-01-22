package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerAddressRepository;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import dev.challenge.api.domain.model.CustomerModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerAddressServiceImpl implements CustomerAddressService {

  private final CustomerAddressRepository customerAddressRepository;
  private final CustomerServiceImpl customerService;

  @Override
  public CustomerAddressModel add(Long customerId, CustomerAddressModel customerAddress) {

    CustomerAddressModel newAddress = CustomerAddressModel.builder()
        .customer(customerService.findById(customerId))
        .street(customerAddress.getStreet())
        .city(customerAddress.getCity())
        .state(customerAddress.getState())
        .zipCode(customerAddress.getZipCode())
        .addressType(customerAddress.getAddressType())
        .isDefault(hasDefaultByCustomerId(customerId) ? Boolean.FALSE : Boolean.TRUE)
        .build();

    return customerAddressRepository.save(newAddress);
  }

  @Override
  public CustomerAddressModel update(Long id, Long customerId, CustomerAddressModel customerAddress) {
    CustomerAddressModel existingAddress = findByIdAndCustomerId(id, customerId);
    existingAddress.setAddressType(customerAddress.getAddressType());
    existingAddress.setStreet(customerAddress.getStreet());
    existingAddress.setCity(customerAddress.getCity());
    existingAddress.setState(customerAddress.getState());
    existingAddress.setZipCode(customerAddress.getZipCode());
    return customerAddressRepository.save(existingAddress);
  }

  @Override
  public CustomerAddressModel findByIdAndCustomerId(Long id, Long customerId) {
    Example<CustomerAddressModel> filter = Example.of(CustomerAddressModel.builder()
        .id(id)
        .customer(CustomerModel.builder().id(customerId).build())
        .build());

    return customerAddressRepository
        .findAll(filter)
        .stream()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("CustomerAddress not found with CustomerId " + customerId + " and CustomerAddressId " + id));
  }

  @Override
  public List<CustomerAddressModel> findAllByCustomerId(Long customerId) {
    Example<CustomerAddressModel> filter = Example.of(CustomerAddressModel.builder()
        .customer(CustomerModel.builder().id(customerId).build())
        .build());

    return customerAddressRepository.findAll(filter)
        .stream()
        .toList();
  }

  private Boolean hasDefaultByCustomerId(Long customerId) {
    Example<CustomerAddressModel> filter = Example.of(CustomerAddressModel.builder()
        .customer(CustomerModel.builder().id(customerId).build())
        .isDefault(Boolean.TRUE)
        .build());

    return customerAddressRepository
        .findAll(filter)
        .stream()
        .count() > 0;
  }
}
