package br.com.leonardo.customer_service.adapters.inbound.rest;

import br.com.leonardo.customer_service.application.CustomerService;
import br.com.leonardo.customer_service.application.dto.CreateCustomerRequest;
import br.com.leonardo.customer_service.application.dto.CustomerResponse;
import br.com.leonardo.customer_service.config.JwtUtil;
import br.com.leonardo.customer_service.domain.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Customer management API")
public class CustomerController {

    private final CustomerService customerService;
    private final JwtUtil jwtUtil;

    public CustomerController(CustomerService customerService, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new customer")
    public ResponseEntity<CustomerResponse> register(@Valid @RequestBody CreateCustomerRequest request) {
        Customer customer = customerService.register(request);
        return ResponseEntity.status(201).body(CustomerResponse.fromCustomer(customer));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find customer by ID")
    public ResponseEntity<CustomerResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(CustomerResponse.fromCustomer(customerService.findById(id)));
    }

    @GetMapping("/me")
    @Operation(summary = "Find current customer profile")
    public ResponseEntity<CustomerResponse> getCurrentUser(@RequestHeader(name = "Authorization", required = false) String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new InvalidCredentialsException("Missing or invalid Authorization header");
        }

        String token = authorization.replace("Bearer ", "");
        if (!jwtUtil.validateToken(token)) {
            throw new InvalidCredentialsException("Invalid or expired token");
        }

        String email = jwtUtil.extractEmail(token);
        Customer customer = customerService.findByEmail(email);
        return ResponseEntity.ok(CustomerResponse.fromCustomer(customer));
    }

    @GetMapping
    @Operation(summary = "List all active customers")
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(customerService.findAll().stream()
                .map(CustomerResponse::fromCustomer)
                .toList());
    }
}
