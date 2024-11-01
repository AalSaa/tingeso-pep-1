package com.example.PrestaBancoBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "loans")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Property value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Property value must be positive")
    @JsonProperty("property_value")
    private BigDecimal propertyValue;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Term is required")
    @Min(value = 0, message = "Term must be positive")
    @JsonProperty("term_in_years")
    private Integer termInYears;

    @NotNull(message = "Annual interest percentage rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Annual interest percentage rate must be positive")
    @JsonProperty("annual_interest_rate_percentage")
    private Double annualInterestRatePercentage;

    @NotNull(message = "Monthly life insurance percentage is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly life insurance percentage must be positive")
    @JsonProperty("monthly_life_insurance_percentage")
    private Double monthlyLifeInsurancePercentage;

    @NotNull(message = "Monthly fire insurance percentage is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly fire insurance percentage must be positive")
    @JsonProperty("monthly_fire_insurance_amount_percentage")
    private BigDecimal monthlyFireInsuranceAmountPercentage;

    @NotNull(message = "Administration fee percentage is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Administration fee must be positive")
    @JsonProperty("administration_fee_percentage")
    private Double administrationFeePercentage;

    @NotEmpty(message = "Status is required")
    private String status;

    @JsonProperty("monthly_cost")
    private BigDecimal monthlyCost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanTypeEntity loanType;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL)
    @JsonIgnore
    private EvaluationInfoEntity evaluationInfo;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DocumentEntity> documents;
}
