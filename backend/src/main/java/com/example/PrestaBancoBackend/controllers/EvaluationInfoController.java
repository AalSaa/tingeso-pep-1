package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.dtos.EvaluationInfoDTO;
import com.example.PrestaBancoBackend.dtos.EvaluationInfoUpdateDTO;
import com.example.PrestaBancoBackend.entities.EvaluationInfoEntity;
import com.example.PrestaBancoBackend.services.EvaluationInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluation_infos")
public class EvaluationInfoController {
    @Autowired
    private EvaluationInfoService evaluationInfoService;

    @GetMapping
    public ResponseEntity<List<EvaluationInfoEntity>> getEvaluationInfos() {
        return new ResponseEntity<>(evaluationInfoService.getAllEvaluationInfo(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationInfoEntity> getEvaluationInfoById(@PathVariable Long id) {
        return new ResponseEntity<>(evaluationInfoService.getEvaluationInfoById(id), HttpStatus.OK);
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<EvaluationInfoEntity> getEvaluationInfoByLoanId(@PathVariable Long loanId) {
        return new ResponseEntity<>(evaluationInfoService.getEvaluationInfoByLoanId(loanId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EvaluationInfoEntity> addEvaluationInfo(
            @RequestBody EvaluationInfoDTO evaluationInfoDTO
    ) {
        return new ResponseEntity<>(evaluationInfoService.saveEvaluationInfo(evaluationInfoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationInfoEntity> updateEvaluationInfo(
            @PathVariable Long id,@RequestBody EvaluationInfoUpdateDTO evaluationInfoDTO
    ) {
        return new ResponseEntity<>(evaluationInfoService.updateEvaluationInfo(id, evaluationInfoDTO), HttpStatus.OK);
    }
}
