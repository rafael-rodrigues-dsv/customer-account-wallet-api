package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerRepository;
import dev.challenge.api.domain.CustomerService;
import dev.challenge.api.domain.model.CustomerModel;
import dev.challenge.api.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Override
  public CustomerModel add(CustomerModel customer) {
    if (hasCustomerWithDocumentNumber(customer.getDocumentNumber())) {
      throw new BusinessException("A customer with document number " + customer.getDocumentNumber() + " already exists.");
    }

    customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));

    return customerRepository.save(customer);
  }

  @Override
  public CustomerModel update(Long id, CustomerModel customer) {
    return customerRepository.findById(id).map(existingCustomer -> {
      existingCustomer.setName(customer.getName());
      existingCustomer.setEmail(customer.getEmail());
      existingCustomer.setPhoneNumber(customer.getPhoneNumber());
      return customerRepository.save(existingCustomer);
    }).orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
  }

  @Override
  public CustomerModel findById(Long id) {
    return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
  }

  public Boolean checkPassword(Long id, String password) {
    return customerRepository.findById(id).map(existingCustomer -> {
      return new BCryptPasswordEncoder().matches(password, existingCustomer.getPassword());
    }).orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
  }

  private Boolean hasCustomerWithDocumentNumber(String document) {
    Example<CustomerModel> filter = Example.of(CustomerModel.builder()
        .documentNumber(document)
        .build());

    return customerRepository.exists(filter);
  }
}
