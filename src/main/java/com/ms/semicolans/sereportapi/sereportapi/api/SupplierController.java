package com.ms.semicolans.sereportapi.sereportapi.api;

import com.ms.semicolans.sereportapi.sereportapi.entity.main.Supplier;
import com.ms.semicolans.sereportapi.sereportapi.service.SupplierService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class SupplierController {

    private static final Logger log = LoggerFactory.getLogger(SupplierController.class);
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostConstruct
    public void init() {
        log.error("🚀🚀🚀 SupplierController BEAN CREATED 🚀🚀🚀");
    }

    // ---- PUBLIC TEST (no /api/) ----
    @GetMapping("/public-supplier-details")
    public ResponseEntity<Map<String, Object>> publicTest() {
        log.error("### publicTest() called ###");
        List<Supplier> all = supplierService.getAllSuppliers();
        Map<String, Object> inner = new LinkedHashMap<>();
        inner.put("data", all);
        inner.put("count", all.size());
        inner.put("totalOutstandingAmount", BigDecimal.ZERO);
        return ResponseEntity.ok(Map.of("data", inner));
    }

    // ---- /api/ endpoints ----
    @GetMapping("/api/suppliers/ping")
    public ResponseEntity<String> ping() {
        log.error("### SupplierController.ping() called ###");
        return ResponseEntity.ok("SupplierController is alive");
    }

    @GetMapping("/api/suppliers/get-all-suppliers-name-list")
    public ResponseEntity<Map<String, Object>> getAllSupplierNames() {
        List<String> names = supplierService.getAllSupplierNames();
        return ResponseEntity.ok(Map.of("data", names));
    }

    @GetMapping("/api/suppliers/supplier-details")
    public ResponseEntity<Map<String, Object>> getSupplierDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String supplierSearch,
            @RequestParam(defaultValue = "") String creditSearch,
            @RequestParam(defaultValue = "All") String invGap,
            @RequestParam(defaultValue = "All") String settlementGap) {

        log.error("### getSupplierDetails() called ###");
        List<Supplier> all = supplierService.getAllSuppliers();
        Map<String, Object> inner = new LinkedHashMap<>();
        inner.put("data", all);
        inner.put("count", all.size());
        inner.put("totalOutstandingAmount", BigDecimal.ZERO);
        return ResponseEntity.ok(Map.of("data", inner));
    }

    @GetMapping("/api/suppliers-creditor/get-creditor-details-list")
    public ResponseEntity<Map<String, Object>> getCreditorDetailsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String supplierSearch,
            @RequestParam(defaultValue = "") String creditSearch,
            @RequestParam(defaultValue = "All") String invGap,
            @RequestParam(defaultValue = "All") String settlementGap) {

        log.error("### getCreditorDetailsList() called ###");
        Map<String, Object> inner = new LinkedHashMap<>();
        inner.put("data", Collections.emptyList());
        inner.put("count", 0);
        inner.put("totalOutstandingAmount", BigDecimal.ZERO);
        return ResponseEntity.ok(Map.of("data", inner));
    }

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

        log.error("### getPayableDetails() called ###");
        Map<String, Object> inner = new LinkedHashMap<>();
        inner.put("data", Collections.emptyList());
        inner.put("count", 0);
        inner.put("totalOutstandingAmount", BigDecimal.ZERO);
        return ResponseEntity.ok(Map.of("data", inner));
    }
}