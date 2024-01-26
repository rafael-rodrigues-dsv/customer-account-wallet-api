package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerAddressRepository;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.CustomerService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import dev.challenge.api.domain.model.CustomerModel;
import dev.challenge.api.exception.DomainRuleException;
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
    return customerAddressRepository.save(
        CustomerAddressModel.builder()
            .customer(customerService.findById(customerId))
            .street(customerAddress.getStreet())
            .city(customerAddress.getCity())
            .state(customerAddress.getState())
            .zipCode(customerAddress.getZipCode())
            .addressType(customerAddress.getAddressType())
            .isDefault(hasDefaultByCustomerId(customerId) ? Boolean.FALSE : Boolean.TRUE)
            .build()
    );
  }

  @Override
  public CustomerAddressModel update(Long id, Long customerId, CustomerAddressModel customerAddress) {
    CustomerAddressModel currentAddress = findByIdAndVerifyCustomerId(id, customerId);
    currentAddress.setAddressType(customerAddress.getAddressType());
    currentAddress.setStreet(customerAddress.getStreet());
    currentAddress.setCity(customerAddress.getCity());
    currentAddress.setState(customerAddress.getState());
    currentAddress.setZipCode(customerAddress.getZipCode());
    return customerAddressRepository.save(currentAddress);
  }

  @Override
  public CustomerAddressModel findByIdAndVerifyCustomerId(Long id, Long customerId) {
    return customerAddressRepository.findById(id).map(existingAddress -> {
      if (!existingAddress.getCustomer().getId().equals(customerId)) {
        throw new DomainRuleException("Address with ID " + existingAddress.getId() +
            " does not belong to customer with ID " + customerId);
      }
      return existingAddress;
    }).orElseThrow(() -> new EntityNotFoundException("Address not found with ID " + id));
  }

  @Override
  public List<CustomerAddressModel> findAllByCustomerId(Long customerId) {
    return customerAddressRepository.findAll(
        Example.of(
            CustomerAddressModel.builder()
                .customer(CustomerModel.builder().id(customerId).build())
                .build()
        )
    );
  }

  private Boolean hasDefaultByCustomerId(Long customerId) {
    return customerAddressRepository.exists(
        Example.of(
            CustomerAddressModel.builder()
                .customer(CustomerModel.builder().id(customerId).build())
                .isDefault(Boolean.TRUE)
                .build()
        )
    );
  }
}
