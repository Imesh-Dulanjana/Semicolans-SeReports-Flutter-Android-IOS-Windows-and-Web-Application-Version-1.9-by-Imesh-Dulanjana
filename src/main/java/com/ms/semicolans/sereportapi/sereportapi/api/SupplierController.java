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

    // ---------- Supplier details – returns plain list ----------
    @GetMapping("/api/suppliers/supplier-details")
    public ResponseEntity<Map<String, Object>> getSupplierDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String supplierSearch,
            @RequestParam(defaultValue = "") String creditSearch,
            @RequestParam(defaultValue = "All") String invGap,
            @RequestParam(defaultValue = "All") String settlementGap) {

        log.info("SupplierController: supplier-details");
        List<Supplier> all = supplierService.getAllSuppliers();
        return ResponseEntity.ok(Map.of("data", all));   // plain list, no pagination
    }

    // ---------- Creditor details – plain empty list ----------
    @GetMapping("/api/suppliers-creditor/get-creditor-details-list")
    public ResponseEntity<Map<String, Object>> getCreditorDetailsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String supplierSearch,
            @RequestParam(defaultValue = "") String creditSearch,
            @RequestParam(defaultValue = "All") String invGap,
            @RequestParam(defaultValue = "All") String settlementGap) {

        log.info("SupplierController: creditor-details");
        return ResponseEntity.ok(Map.of("data", Collections.emptyList()));
    }

    // ---------- Payable details – plain empty list ----------
    @GetMapping("/api/suppliers/payable-details")
    public ResponseEntity<Map<String, Object>> getPayableDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "All") String locaCode,
            @RequestParam(defaultValue = "") String searchSupplier,
            @RequestParam(defaultValue = "") String searchInvoice,
            @RequestParam(defaultValue = "All") String invGap,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {

        log.info("SupplierController: payable-details");
        return ResponseEntity.ok(Map.of("data", Collections.emptyList()));
    }
}