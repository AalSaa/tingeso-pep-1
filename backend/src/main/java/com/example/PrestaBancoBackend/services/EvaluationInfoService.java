package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.entities.EvaluationInfoEntity;
import com.example.PrestaBancoBackend.repositories.EvaluationInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationInfoService {
    @Autowired
    private EvaluationInfoRepository evaluationInfoRepository;

    public List<EvaluationInfoEntity> getAllEvaluationInfo(){
        return evaluationInfoRepository.findAll();
    }

    public EvaluationInfoEntity getEvaluationInfoById(Long id){
        Optional<EvaluationInfoEntity> evaluationInfo = evaluationInfoRepository.findById(id);

        if (evaluationInfo.isEmpty()) {
            throw new EntityNotFoundException("Evaluation info not found");
        }

        return evaluationInfo.get();
    }
}
