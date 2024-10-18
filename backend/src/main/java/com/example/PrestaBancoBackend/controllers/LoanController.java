package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.dtos.LoanCreateDTO;
import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.services.LoanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping
    ResponseEntity<List<LoanEntity>> getLoans(){
        return new ResponseEntity<>(loanService.getAllLoans(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<LoanEntity> getLoanById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(loanService.getLoanById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    ResponseEntity<LoanEntity> postLoan(@RequestBody LoanCreateDTO loanDTO) {
        try {
            return new ResponseEntity<>(loanService.createLoan(loanDTO), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<LoanEntity> deleteLoan(@PathVariable Long id){
        try {
            loanService.deleteLoanById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
