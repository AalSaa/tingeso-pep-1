package com.example.PrestaBancoBackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCreateDTO {
    @JsonProperty("property_value")
    private BigDecimal propertyValue;
    private BigDecimal amount;
    @JsonProperty("term_in_years")
    private Integer termInYears;
    @JsonProperty("annual_interest_rate")
    private Double annualInterestRate;
    @JsonProperty("monthly_life_insurance")
    private Double monthlyLifeInsurance;
    @JsonProperty("monthly_fire_insurance")
    private Double monthlyFireInsurance;
    @JsonProperty("administration_fee")
    private Double administrationFee;
    private String status;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("loan_type_id")
    private Long loanTypeId;
}
