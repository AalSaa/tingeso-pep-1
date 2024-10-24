package com.example.PrestaBancoBackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluations")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Validation of income to expense ratio is required")
    @JsonProperty("is_income_expense_ratio_valid")
    private Boolean isIncomeExpenseRatioValid;

    @NotNull(message = "Validation of credit history is required")
    @JsonProperty("is_credit_history_valid")
    private Boolean isCreditHistoryValid;

    @NotNull(message = "Validation of employment stability is required")
    @JsonProperty("is_employment_stability_valid")
    private Boolean isEmploymentStabilityValid;

    @NotNull(message = "Validation of debt income ratio is required")
    @JsonProperty("is_debt_income_ratio_valid")
    private Boolean isDebtIncomeRatioValid;

    @NotNull(message = "Validation of age at loan end is required")
    @JsonProperty("is_age_at_loan_end_valid")
    private Boolean isAgeAtLoanEndValid;

    @NotNull(message = "Validation of minimum balance required is required")
    @JsonProperty("is_minimum_balance_required_valid")
    private Boolean isMinimumBalanceRequiredValid;

    @NotNull(message = "Validation of consistent savings history is required")
    @JsonProperty("is_consistent_savings_history_valid")
    private Boolean isConsistentSavingsHistoryValid;

    @NotNull(message = "Validation of periodic deposits is required")
    @JsonProperty("is_periodic_deposits_valid")
    private Boolean isPeriodicDepositsValid;

    @NotNull(message = "Validation of balance years ratio is required")
    @JsonProperty("is_balance_years_ratio_valid")
    private Boolean isBalanceYearsRatioValid;

    @NotNull(message = "Validation of recent withdrawals is required")
    @JsonProperty("is_recent_withdrawals_valid")
    private Boolean isRecentWithdrawalsValid;

    @JsonProperty("evaluation_result")
    private String evaluationResult;

    @OneToOne
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    private LoanEntity loan;
}
