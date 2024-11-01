package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.EvaluationInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationInfoRepository extends JpaRepository<EvaluationInfoEntity, Long> {
    Optional<EvaluationInfoEntity> findByLoanId(Long id);
}
