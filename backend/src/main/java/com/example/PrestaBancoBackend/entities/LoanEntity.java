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

    @NotNull(message = "Annual interest rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Annual interest rate must be positive")
    @JsonProperty("annual_interest_rate")
    private Double annualInterestRate;

    @NotNull(message = "Monthly life insurance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly life insurance must be positive")
    @JsonProperty("monthly_life_insurance")
    private Double monthlyLifeInsurance;

    @NotNull(message = "Monthly fire insurance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly fire insurance must be positive")
    @JsonProperty("monthly_fire_insurance")
    private Double monthlyFireInsurance;

    @NotNull(message = "Administration fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Administration fee must be positive")
    @JsonProperty("administration_fee")
    private Double administrationFee;

    @NotEmpty(message = "Status is required")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanTypeEntity loanType;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DocumentEntity> documents;
}
