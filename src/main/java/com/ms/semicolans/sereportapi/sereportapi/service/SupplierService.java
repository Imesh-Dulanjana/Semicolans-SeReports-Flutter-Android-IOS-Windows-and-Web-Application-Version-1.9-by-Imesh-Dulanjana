package com.ms.semicolans.sereportapi.sereportapi.service;

import com.ms.semicolans.sereportapi.sereportapi.entity.main.Supplier;
import com.ms.semicolans.sereportapi.sereportapi.repo.SupplierRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepo supplierRepo;

    public SupplierService(SupplierRepo supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    public List<String> getAllSupplierNames() {
        // Return only names for dropdown
        return supplierRepo.findAll()
                .stream()
                .map(Supplier::getSupName)
                .filter(name -> name != null && !name.isBlank())
                .collect(Collectors.toList());
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll();
    }
}