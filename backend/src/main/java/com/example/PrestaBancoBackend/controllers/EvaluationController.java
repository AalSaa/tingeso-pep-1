package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.dtos.EvaluationCreateDTO;
import com.example.PrestaBancoBackend.entities.EvaluationEntity;
import com.example.PrestaBancoBackend.services.EvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/evaluations")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @GetMapping
    public ResponseEntity<List<EvaluationEntity>> getEvaluations() {
        return new ResponseEntity<>(evaluationService.getAllEvaluations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationEntity> getEvaluation(@PathVariable Long id) {
        return new ResponseEntity<>(evaluationService.getEvaluationById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EvaluationEntity> postEvaluation(@Valid @RequestBody EvaluationCreateDTO evaluationDTO) {
        return new ResponseEntity<>(evaluationService.createEvaluation(evaluationDTO), HttpStatus.CREATED);
    }
}
