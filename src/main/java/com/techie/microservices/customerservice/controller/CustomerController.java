package com.techie.microservices.customerservice.controller;


import com.techie.microservices.customerservice.dto.CustomerRegistrationRequest;
import com.techie.microservices.customerservice.dto.CustomerRegistrationResponse;
import com.techie.microservices.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerRegistrationResponse> create(
            @RequestBody @Valid CustomerRegistrationRequest request) {

        log.info("Received registration request : {} ",request);
        CustomerRegistrationResponse response = customerService.create(request);
        log.info("Customer registered successfully with id {}", response.getCustomerId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{customerId}/registration")
    public ResponseEntity<CustomerRegistrationResponse> getCustomerById(@PathVariable UUID customerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getCustomerById(customerId));
    }
}
