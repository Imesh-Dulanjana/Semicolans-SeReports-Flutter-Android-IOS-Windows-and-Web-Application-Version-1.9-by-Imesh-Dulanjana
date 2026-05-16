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

    // Endpoint: /api/suppliers/get-all-suppliers-name-list
    @GetMapping("/get-all-suppliers-name-list")
    public ResponseEntity<List<String>> getAllSupplierNames() {
        List<String> names = supplierService.getAllSupplierNames();
        return ResponseEntity.ok(names);
    }

    // Endpoint: /api/suppliers/supplier-details
    @GetMapping("/supplier-details")
    public ResponseEntity<Map<String, Object>> getSupplierDetails(
            @RequestParam(required = false) String searchText) {
        List<Supplier> all = supplierService.getAllSuppliers();
        return ResponseEntity.ok(Map.of("data", all));
    }

    // Endpoint: /api/suppliers/payable-details (return empty for now)
    @GetMapping("/payable-details")
    public ResponseEntity<Map<String, Object>> getSupplierPayableList() {
        return ResponseEntity.ok(Map.of("data", Collections.emptyList()));
    }

    // Endpoint: /api/suppliers-creditor/get-creditor-details-list
    // (Note: the Flutter app expects /api/suppliers-creditor/..., so we add a separate mapping)
    @GetMapping("/../suppliers-creditor/get-creditor-details-list")
    public ResponseEntity<Map<String, Object>> getCreditorDetailsList() {
        return ResponseEntity.ok(Map.of("data", Collections.emptyList()));
    }
}