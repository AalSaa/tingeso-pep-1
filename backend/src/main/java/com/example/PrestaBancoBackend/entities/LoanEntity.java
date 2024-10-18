package com.example.PrestaBancoBackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
