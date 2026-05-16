package com.ms.semicolans.sereportapi.sereportapi.api;

import com.ms.semicolans.sereportapi.sereportapi.dto.responsedto.paginated.PaginatedResponseEmployeeDTO;
import com.ms.semicolans.sereportapi.sereportapi.service.EmployeeService;
import com.ms.semicolans.sereportapi.sereportapi.service.impl.EmployeeServiceImpl;
import com.ms.semicolans.sereportapi.sereportapi.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(path = "/employee-details", params = {"page", "size"})
    public ResponseEntity<StandardResponse> getEmployeeDetails(
            @RequestParam int page,
            @RequestParam int size,
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String searchText) throws SQLException {

        PaginatedResponseEmployeeDTO response = employeeService.getEmployeeDetails(
                token,
                searchText,
                page,
                size
        );

        return ResponseEntity.ok(
                new StandardResponse(
                        200,
                        "Employee details retrieved successfully",
                        response
                )
        );
    }
}