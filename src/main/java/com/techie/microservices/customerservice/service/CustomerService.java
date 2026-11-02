package com.techie.microservices.customerservice.service;

import com.techie.microservices.customerservice.dto.CustomerRegistrationRequest;
import com.techie.microservices.customerservice.dto.CustomerRegistrationResponse;
import com.techie.microservices.customerservice.entity.Customer;
import com.techie.microservices.customerservice.exception.CustomerNotFoundException;
import com.techie.microservices.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerRegistrationResponse create(CustomerRegistrationRequest request) {
        log.info("Creating Customer with email {}", request.getEmail());
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("cutomer already exists with emailId: " + request.getEmail());
        }

        Customer savedCustomer = customerRepository.save(customer);
        log.info("Saved Customer with id {}", savedCustomer.getCustomerId());

        return mapToResponse(savedCustomer);
    }


    public CustomerRegistrationResponse getCustomerById(UUID customerId) {
        log.info("Retrieving Customer with id {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer not found with id: " + customerId)
                );

        log.info("Retrieved Customer with id {}", customerId);
        return mapToResponse(customer);
    }

    private CustomerRegistrationResponse mapToResponse(Customer customer) {

        return CustomerRegistrationResponse.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
