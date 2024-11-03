package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.dtos.LoanDTO;
import com.example.PrestaBancoBackend.dtos.LoanUpdateDTO;
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

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LoanEntity>> getLoansByStatus(@PathVariable String status){
        return new ResponseEntity<>(loanService.getAllLoansByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/status/not/{status}")
    public ResponseEntity<List<LoanEntity>> getLoansByStatusNot(@PathVariable String status){
        return new ResponseEntity<>(loanService.getAllLoansByStatusNot(status), HttpStatus.OK);
    }

    @GetMapping("/status/not/{status}/user/{userId}")
    public ResponseEntity<List<LoanEntity>> getLoansByUserIdAndStatusNot(
            @PathVariable Long userId, @PathVariable String status
    ){
        return new ResponseEntity<>(loanService.getAllLoansByUserIdAndStatusNot(userId, status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanEntity> getLoan(@PathVariable Long id){
        return new ResponseEntity<>(loanService.getLoanById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LoanEntity> postLoan(@RequestBody LoanDTO loanDTO) {
        return new ResponseEntity<>(loanService.createLoan(loanDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanEntity> putLoan(@PathVariable Long id, @Valid @RequestBody LoanUpdateDTO loanDTO) {
        return new ResponseEntity<>(loanService.updateLoan(id, loanDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteLoan(@PathVariable Long id){
        loanService.deleteLoanById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("error", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
