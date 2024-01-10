package dev.challenge.api.adapter.entrypoint.dto.customerbankaccount;

import dev.challenge.api.domain.model.CustomerModel;

import java.math.BigDecimal;

public class CustomerBankAccountDto {
  private Long customerBankAccountId;
  private CustomerModel customer;
  private String agency;
  private String accountNumber;
  private BigDecimal balance;
  private Boolean isActive;
}
