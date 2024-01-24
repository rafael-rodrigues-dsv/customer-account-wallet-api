package dev.challenge.api.adapter.database.repository;

import dev.challenge.api.domain.model.CustomerAddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddressModel, Long> {
}
