package com.example.PrestaBancoBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "loan_types")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String name;

    @JsonProperty("max_term")
    private Integer maxTerm;

    @JsonProperty("min_annual_interest_rate")
    private Double minAnnualInterestRate;

    @JsonProperty("max_annual_interest_rate")
    private Double maxAnnualInterestRate;

    @JsonProperty("max_percentage_amount")
    private Integer maxPercentageAmount;

    @ElementCollection
    @JsonProperty("type_of_documents_required")
    private List<String> typeOfDocumentsRequired;

    @OneToMany(mappedBy = "loanType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoanEntity> loans;
}
