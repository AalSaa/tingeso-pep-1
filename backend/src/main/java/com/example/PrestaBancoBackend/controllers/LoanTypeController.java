package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.entities.LoanTypeEntity;
import com.example.PrestaBancoBackend.services.LoanTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/loan_types")
public class LoanTypeController {
    @Autowired
    private LoanTypeService loanTypeService;

    @GetMapping
    public ResponseEntity<List<LoanTypeEntity>> getLoanTypes() {
        return new ResponseEntity<>(loanTypeService.getAllLoanTypes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoanType(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(loanTypeService.getLoanTypeById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}