package com.ms.semicolans.sereportapi.sereportapi.api;

import com.ms.semicolans.sereportapi.sereportapi.entity.main.Supplier;
import com.ms.semicolans.sereportapi.sereportapi.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class SupplierController {

    private static final Logger log = LoggerFactory.getLogger(SupplierController.class);
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // ---------- Supplier names list ----------
    @GetMapping("/api/suppliers/get-all-suppliers-name-list")
    public ResponseEntity<Map<String, Object>> getAllSupplierNames() {
        log.info("SupplierController: get-all-suppliers-name-list");
        List<String> names = supplierService.getAllSupplierNames();
        return ResponseEntity.ok(Map.of("data", names));
    }

    // ---------- Supplier details (records) ----------
    @GetMapping("/api/suppliers/supplier-details")
    public ResponseEntity<Map<String, Object>> getSupplierDetails(
            @RequestParam(required = false) String searchText) {
        log.info("SupplierController: supplier-details");
        List<Supplier> all = supplierService.getAllSuppliers();
        return ResponseEntity.ok(Map.of("data", all));
    }

    // ---------- Payable details ----------
    @GetMapping("/api/suppliers/payable-details")
    public ResponseEntity<Map<String, Object>> getSupplierPayableList() {
        log.info("SupplierController: payable-details");
        return ResponseEntity.ok(Map.of("data", Collections.emptyList()));
    }

    // ---------- Creditor details (absolute path) ----------
    @GetMapping("/api/suppliers-creditor/get-creditor-details-list")
    public ResponseEntity<Map<String, Object>> getCreditorDetailsList() {
        log.info("SupplierController: creditor-details");
        return ResponseEntity.ok(Map.of("data", Collections.emptyList()));
    }
}