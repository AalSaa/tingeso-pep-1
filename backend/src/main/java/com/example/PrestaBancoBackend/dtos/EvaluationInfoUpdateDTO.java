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
public class EvaluationInfoUpdateDTO {
    @NotNull(message = "Monthly income is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly income must be positive")
    @JsonProperty("monthly_income")
    private BigDecimal monthlyIncome;

    @NotNull(message = "Validation of positive credit history is required")
    @JsonProperty("have_positive_credit_history")
    private Boolean havePositiveCreditHistory;

    @NotEmpty(message = "Employment type is required")
    @JsonProperty("employment_type")
    private String employmentType;

    @NotNull(message = "Employment seniority is required")
    @Min(value = 0, message = "Employment seniority must be positive")
    @JsonProperty("employment_seniority")
    private int employmentSeniority;

    @NotNull(message = "Monthly debt is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly debt must be positive")
    @JsonProperty("monthly_debt")
    private BigDecimal monthlyDebt;

    @NotNull(message = "Savings account balance is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Savings account balance must be positive")
    @JsonProperty("savings_account_balance")
    private BigDecimal savingsAccountBalance;

    @NotNull(message = "Validation of periodic deposits is required")
    @JsonProperty("has_periodic_deposits")
    private Boolean hasPeriodicDeposits;

    @NotNull(message = "Sum of deposits is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Sum of deposits must be positive")
    @JsonProperty("sum_of_deposits")
    private BigDecimal sumOfDeposits;

    @NotNull(message = "Old savings account is required")
    @Min(value = 0, message = "Old savings account must be positive")
    @JsonProperty("old_savings_account")
    private Integer oldSavingsAccount;

    @NotNull(message = "Maximum withdrawal in six months is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum withdrawal in six months must be positive")
    @JsonProperty("maximum_withdrawal_in_six_months")
    private BigDecimal maximumWithdrawalInSixMonths;
}
