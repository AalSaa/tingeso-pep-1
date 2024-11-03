package com.example.PrestaBancoBackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanUpdateDTO {
    @NotNull(message = "Property value is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Property value must be greater than or equal to zero")
    @JsonProperty("property_value")
    private BigDecimal propertyValue;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Amount must be greater than or equal to zero")
    private BigDecimal amount;

    @NotNull(message = "Term is required")
    @Min(value = 0, message = "Term must be greater than or equal to zero")
    @JsonProperty("term_in_years")
    private Integer termInYears;

    @NotNull(message = "Annual interest percentage rate is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Annual interest percentage rate must be greater than or equal to zero")
    @JsonProperty("annual_interest_rate_percentage")
    private Double annualInterestRatePercentage;

    @NotNull(message = "Monthly life insurance percentage is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly life insurance percentage must be greater than or equal to zero")
    @JsonProperty("monthly_life_insurance_percentage")
    private Double monthlyLifeInsurancePercentage;

    @NotNull(message = "Monthly fire insurance percentage is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly fire insurance percentage must be greater than or equal to zero")
    @JsonProperty("monthly_fire_insurance_amount_percentage")
    private Double monthlyFireInsuranceAmountPercentage;

    @NotNull(message = "Administration fee percentage is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Administration fee must be greater than or equal to zero")
    @JsonProperty("administration_fee_percentage")
    private Double administrationFeePercentage;

    @NotEmpty(message = "Status is required")
    private String status;
}
