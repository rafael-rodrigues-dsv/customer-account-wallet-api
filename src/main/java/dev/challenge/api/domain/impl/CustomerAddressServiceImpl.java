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
    customerAddress.setCustomer(customerService.findById(customerId));

    if (hasDefaultByCustomerId(customerId))
      customerAddress.setIsDefault(Boolean.FALSE);

    return customerAddressRepository.save(customerAddress);
  }

  @Override
  public CustomerAddressModel update(Long id, Long customerId, CustomerAddressModel customerAddress) {
    CustomerAddressModel existingCustomerAddress = findByIdAndCustomerId(id, customerId);
    existingCustomerAddress.setAddressType(customerAddress.getAddressType());
    existingCustomerAddress.setStreet(customerAddress.getStreet());
    existingCustomerAddress.setCity(customerAddress.getCity());
    existingCustomerAddress.setState(customerAddress.getState());
    existingCustomerAddress.setZipCode(customerAddress.getZipCode());
    return customerAddressRepository.save(existingCustomerAddress);
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
