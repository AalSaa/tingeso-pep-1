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
public class EvaluationInfoDTO {
    @NotNull(message = "Monthly income is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly income must be greater than or equal to zero")
    @JsonProperty("monthly_income")
    private BigDecimal monthlyIncome;

    @NotNull(message = "Validation of positive credit history is required")
    @JsonProperty("have_positive_credit_history")
    private Boolean havePositiveCreditHistory;

    @NotEmpty(message = "Employment type is required")
    @JsonProperty("employment_type")
    private String employmentType;

    @NotNull(message = "Employment seniority is required")
    @Min(value = 0, message = "Employment seniority must be greater than or equal to zero")
    @JsonProperty("employment_seniority")
    private Integer employmentSeniority;

    @NotNull(message = "Monthly debt is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly debt must be greater than or equal to zero")
    @JsonProperty("monthly_debt")
    private BigDecimal monthlyDebt;

    @NotNull(message = "Savings account balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Savings account balance must be greater than or equal to zero")
    @JsonProperty("savings_account_balance")
    private BigDecimal savingsAccountBalance;

    @NotNull(message = "Validation of consistent savings history is required")
    @JsonProperty("has_consistent_savings_history")
    private Boolean hasConsistentSavingsHistory;

    @NotNull(message = "Validation of periodic deposits is required")
    @JsonProperty("has_periodic_deposits")
    private Boolean hasPeriodicDeposits;

    @NotNull(message = "Sum of deposits is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Sum of deposits must be greater than or equal to zero")
    @JsonProperty("sum_of_deposits")
    private BigDecimal sumOfDeposits;

    @NotNull(message = "Old savings account is required")
    @Min(value = 0, message = "Old savings account must be greater than or equal to zero")
    @JsonProperty("old_savings_account")
    private Integer oldSavingsAccount;

    @NotNull(message = "Maximum withdrawal in six months is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Maximum withdrawal in six months must be greater than or equal to zero")
    @JsonProperty("maximum_withdrawal_in_six_months")
    private BigDecimal maximumWithdrawalInSixMonths;

    @NotNull(message = "Loan id is required")
    @JsonProperty("loan_id")
    private Long loanId;
}
