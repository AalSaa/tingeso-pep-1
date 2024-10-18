package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
}
