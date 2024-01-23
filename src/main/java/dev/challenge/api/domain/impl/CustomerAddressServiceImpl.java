package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerAddressRepository;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.CustomerService;
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
  private final CustomerService customerService;

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
  public CustomerAddressModel update(Long id, CustomerAddressModel customerAddress) {
    return customerAddressRepository.findById(id).map(existingAddress -> {
      existingAddress.setAddressType(customerAddress.getAddressType());
      existingAddress.setStreet(customerAddress.getStreet());
      existingAddress.setCity(customerAddress.getCity());
      existingAddress.setState(customerAddress.getState());
      existingAddress.setZipCode(customerAddress.getZipCode());
      return customerAddressRepository.save(existingAddress);
    }).orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
  }

  @Override
  public CustomerAddressModel findById(Long id) {
    return customerAddressRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
  }

  @Override
  public List<CustomerAddressModel> findAllByCustomerId(Long customerId) {
    Example<CustomerAddressModel> filter = Example.of(CustomerAddressModel.builder()
        .customer(CustomerModel.builder().id(customerId).build())
        .build());

    return customerAddressRepository.findAll(filter);
  }

  private Boolean hasDefaultByCustomerId(Long customerId) {
    Example<CustomerAddressModel> filter = Example.of(CustomerAddressModel.builder()
        .customer(CustomerModel.builder().id(customerId).build())
        .isDefault(Boolean.TRUE)
        .build());

    return customerAddressRepository.exists(filter);
  }
}
