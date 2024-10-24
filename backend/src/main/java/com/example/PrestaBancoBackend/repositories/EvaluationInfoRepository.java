package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.EvaluationInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationInfoRepository extends JpaRepository<EvaluationInfoEntity, Long> {
}
