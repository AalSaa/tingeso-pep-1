package com.example.PrestaBancoBackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
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
}
