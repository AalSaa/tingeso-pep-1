package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.entities.EvaluationResultEntity;
import com.example.PrestaBancoBackend.services.EvaluationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/evaluation_results")
public class EvaluationResultController {
    @Autowired
    private EvaluationResultService evaluationResultService;

    @GetMapping
    public ResponseEntity<List<EvaluationResultEntity>> getEvaluations() {
        return new ResponseEntity<>(evaluationResultService.getAllEvaluations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationResultEntity> getEvaluation(@PathVariable Long id) {
        return new ResponseEntity<>(evaluationResultService.getEvaluationById(id), HttpStatus.OK);
    }

    @PostMapping("/loan/{loanId}")
    public ResponseEntity<EvaluationResultEntity> postEvaluation(@PathVariable Long loanId) {
        return new ResponseEntity<>(evaluationResultService.generateEvaluationResult(loanId), HttpStatus.CREATED);
    }
}
