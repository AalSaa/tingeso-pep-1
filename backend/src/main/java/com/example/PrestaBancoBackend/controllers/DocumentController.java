package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.entities.DocumentEntity;
import com.example.PrestaBancoBackend.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping
    public ResponseEntity<List<DocumentEntity>> getDocuments() {
        return new ResponseEntity<>(documentService.getAllDocuments(), HttpStatus.OK);
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<DocumentEntity>> getDocumentsByLoanId(@PathVariable Long loanId) {
        return new ResponseEntity<>(documentService.getAllDocumentsByLoanId(loanId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentEntity> getDocument(@PathVariable Long id) {
        return new ResponseEntity<>(documentService.getDocumentById(id), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        DocumentEntity document = documentService.getDocumentById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(document.getFileType()));
        headers.setContentDispositionFormData("attachment", document.getFileName());
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");

        return new ResponseEntity<>(document.getData(), headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DocumentEntity> postDocument(
            @RequestParam(name = "loan_id") Long loanId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return new ResponseEntity<>(documentService.saveDocument(loanId, file), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocumentById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("error", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
