package com.crm.redis_impl.controller.rest.adminController;

import com.crm.redis_impl.dto.authDto.RegisterRequestDto;
import com.crm.redis_impl.dto.response.ApiResponse;
import com.crm.redis_impl.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/sales")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getSalesAgents() {
        return userService.getAllSalesAgents();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deactivateSalesAgent(@PathVariable Long id) {
        return userService.softDeleteSalesAgent(id);
    }

    @PostMapping("/add-sales")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addSalesPerson(@RequestBody RegisterRequestDto request) {
        request.setRole("ROLE_SALES"); // Force the role for security
        return userService.registerNewUser(request);
    }
}