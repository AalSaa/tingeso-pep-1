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
    @JsonProperty("user_id")
    private Long userId;
}
