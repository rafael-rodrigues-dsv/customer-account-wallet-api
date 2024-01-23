package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerRepository;
import dev.challenge.api.domain.CustomerService;
import dev.challenge.api.domain.model.CustomerModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Override
  public CustomerModel add(CustomerModel customer) {
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
}
