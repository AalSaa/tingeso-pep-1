package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    List<DocumentEntity> findAllByLoanId(Long loanId);
}
