package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.dtos.LoanCreateDTO;
import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<List<LoanEntity>> getLoans(){
        return new ResponseEntity<>(loanService.getAllLoans(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanEntity> getLoan(@PathVariable Long id){
        return new ResponseEntity<>(loanService.getLoanById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LoanEntity> postLoan(@Valid @RequestBody LoanCreateDTO loanDTO) {
        return new ResponseEntity<>(loanService.createLoan(loanDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteLoan(@PathVariable Long id){
        loanService.deleteLoanById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("error", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
