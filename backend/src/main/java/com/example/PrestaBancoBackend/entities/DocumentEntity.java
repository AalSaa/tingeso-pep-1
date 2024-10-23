package com.example.PrestaBancoBackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "documents")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_type")
    private String fileType;

    @Column(columnDefinition = "bytea")
    private byte[] data;

    @CreationTimestamp
    @JsonProperty("upload_date")
    private Date uploadDate;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanEntity loan;
}
