package com.example.PrestaBancoBackend.dtos;

import com.example.PrestaBancoBackend.entities.LoanEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDTO {
    private LoanEntity loan;
    private BigDecimal monthlyCost;
}
