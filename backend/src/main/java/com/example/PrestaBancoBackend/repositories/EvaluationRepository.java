package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {
}
