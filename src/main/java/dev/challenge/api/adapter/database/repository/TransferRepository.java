package dev.challenge.api.adapter.database.repository;

import dev.challenge.api.domain.model.TransferModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository  extends JpaRepository<TransferModel, Long> {
}
