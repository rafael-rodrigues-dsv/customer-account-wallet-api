package dev.challenge.api.adapter.database.repository;

import dev.challenge.api.domain.model.CustomerBankAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerBankAccountRepository extends JpaRepository<CustomerBankAccountModel, Long> {
}
