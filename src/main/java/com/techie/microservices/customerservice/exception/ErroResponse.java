package com.techie.microservices.customerservice.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErroResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;
}
