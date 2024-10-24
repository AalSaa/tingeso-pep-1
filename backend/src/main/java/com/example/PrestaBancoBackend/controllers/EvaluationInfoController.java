package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.entities.EvaluationInfoEntity;
import com.example.PrestaBancoBackend.services.EvaluationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<EvaluationInfoEntity> getEvaluationInfoById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(evaluationInfoService.getEvaluationInfoById(id), HttpStatus.OK);
    }
}
