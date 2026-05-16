package com.ms.semicolans.sereportapi.sereportapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/suppliers-creditor")
public class SupplierCreditorController {

    @GetMapping("/get-creditor-details-list")
    public ResponseEntity<Map<String, Object>> getCreditorDetailsList() {
        // Return empty list wrapped in "data"
        return ResponseEntity.ok(Map.of("data", Collections.emptyList()));
    }
}