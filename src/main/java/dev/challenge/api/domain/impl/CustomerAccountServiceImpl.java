package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerAccountRepository;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.CustomerService;
import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import dev.challenge.api.domain.model.CustomerAccountModel;
import dev.challenge.api.domain.model.CustomerModel;
import dev.challenge.api.exception.DomainRuleException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerAccountServiceImpl implements CustomerAccountService {

  private final CustomerAccountRepository customerAccountRepository;
  private final CustomerService customerService;

  @Override
  public CustomerAccountModel add(Long customerId, CustomerAccountModel customerAccount) {
    if (hasAccountWithAccountNumber(customerAccount.getAccountNumber())) {
      throw new DomainRuleException("An account with document number " + customerAccount.getAccountNumber() + " already exists.");
    }

    CustomerAccountModel newAccount = CustomerAccountModel.builder()
        .customer(customerService.findById(customerId))
        .agency(customerAccount.getAgency())
        .accountNumber(customerAccount.getAccountNumber())
        .balance(customerAccount.getBalance())
        .isDefault(hasDefaultByCustomerId(customerId) ? Boolean.FALSE : Boolean.TRUE)
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .build();

    return customerAccountRepository.save(newAccount);
  }

  @Override
  public CustomerAccountModel update(Long id, Long customerId, CustomerAccountModel customerAccount) {
    CustomerAccountModel currentAccount = findByIdAndVerifyCustomerId(id, customerId);
    currentAccount.setAgency(customerAccount.getAgency());
    currentAccount.setAccountNumber(customerAccount.getAccountNumber());
    return customerAccountRepository.save(currentAccount);
  }

  @Override
  public CustomerAccountModel updateBalance(Long id, Long customerId, BigDecimal balance) {
    CustomerAccountModel currentAccount = findByIdAndVerifyCustomerId(id, customerId);
    CustomerAccountStatusEnum accountStatus = currentAccount.getAccountStatus();

    if (!accountStatus.equals(CustomerAccountStatusEnum.ACTIVE)) {
      String errorMessage = accountStatus.equals(CustomerAccountStatusEnum.BLOCKED) ?
          "Balance cannot be updated because Account is blocked with id " + id :
          "Balance cannot be updated because Account is disabled with id " + id;
      throw new DomainRuleException(errorMessage);
    }

    currentAccount.setBalance(balance);
    return customerAccountRepository.save(currentAccount);
  }

  @Override
  public CustomerAccountModel updateAccountStatus(Long id, Long customerId, CustomerAccountStatusEnum accountStatus) {
    CustomerAccountModel currentAccount = findByIdAndVerifyCustomerId(id, customerId);
    currentAccount.setAccountStatus(accountStatus);
    return customerAccountRepository.save(currentAccount);
  }

  @Override
  public CustomerAccountModel findById(Long id) {
    return customerAccountRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Account not found with ID " + id));
  }

  @Override
  public CustomerAccountModel findByIdAndVerifyCustomerId(Long id, Long customerId) {
    CustomerAccountModel currentAccount = findById(id);

    if (!currentAccount.getCustomer().getId().equals(customerId)) {
      throw new DomainRuleException("Account with ID " + currentAccount.getId() +
          " does not belong to customer with ID " + customerId);
    }

    return currentAccount;
  }

  @Override
  public List<CustomerAccountModel> findAllByCustomerId(Long customerId) {
    return customerAccountRepository.findAll(
            Example.of(
                CustomerAccountModel.builder()
                    .customer(CustomerModel.builder().id(customerId).build())
                    .build()
            ))
        .stream()
        .toList();
  }

  private Boolean hasDefaultByCustomerId(Long customerId) {
    return customerAccountRepository.exists(
        Example.of(
            CustomerAccountModel.builder()
                .customer(CustomerModel.builder().id(customerId).build())
                .isDefault(Boolean.TRUE)
                .build()
        ));
  }

  private Boolean hasAccountWithAccountNumber(String document) {
    return customerAccountRepository.exists(
        Example.of(
            CustomerAccountModel.builder()
                .accountNumber(document)
                .build()
        ));
  }
}
