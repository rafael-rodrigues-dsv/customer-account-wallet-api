package dev.challenge.api.adapter.database.repository;

import dev.challenge.api.domain.model.CustomerAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccountModel, Long> {
}
