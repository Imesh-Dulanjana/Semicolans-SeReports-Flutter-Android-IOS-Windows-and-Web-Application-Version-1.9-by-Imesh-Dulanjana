package com.ms.semicolans.sereportapi.sereportapi.api;

import com.ms.semicolans.sereportapi.sereportapi.entity.main.Supplier;
import com.ms.semicolans.sereportapi.sereportapi.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // /api/suppliers/get-all-suppliers-name-list
    @GetMapping("/get-all-suppliers-name-list")
    public ResponseEntity<Map<String, Object>> getAllSupplierNames() {
        List<String> names = supplierService.getAllSupplierNames();
        return ResponseEntity.ok(Map.of("data", names));   // ← wrapped in "data"
    }

    // /api/suppliers/supplier-details
    @GetMapping("/supplier-details")
    public ResponseEntity<Map<String, Object>> getSupplierDetails(
            @RequestParam(required = false) String searchText) {
        List<Supplier> all = supplierService.getAllSuppliers();
        return ResponseEntity.ok(Map.of("data", all));     // ← wrapped in "data"
    }

    // /api/suppliers/payable-details
    @GetMapping("/payable-details")
    public ResponseEntity<Map<String, Object>> getSupplierPayableList() {
        // Return empty list (not map) so the app can cast it as List
        return ResponseEntity.ok(Map.of("data", Collections.emptyList()));
    }
}