package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.EvaluationResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationResultRepository extends JpaRepository<EvaluationResultEntity, Long> {
}
