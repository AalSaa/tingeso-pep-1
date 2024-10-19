package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.LoanTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTypeRepository extends JpaRepository<LoanTypeEntity, Long> {
}
