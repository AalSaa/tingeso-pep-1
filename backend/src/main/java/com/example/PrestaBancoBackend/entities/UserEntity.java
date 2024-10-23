package com.example.PrestaBancoBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotEmpty(message = "Rut is required")
    private String rut;

    @NotEmpty(message = "First name is required")
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Birth date is required")
    @Temporal(TemporalType.DATE)

    @JsonProperty("birth_date")
    private Date birthDate;

    @NotEmpty(message = "Status is required")
    private String status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoanEntity> loans;
}