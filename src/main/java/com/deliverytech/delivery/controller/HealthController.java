package com.deliverytech.delivery.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController {
    
    @GetMapping("/health")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    public Map<String, String> helth(){
    return Map.of(
        "status", "UP",
        "timestamp", LocalDateTime.now().toString(),
        "Service", "Delivery API"
    );
    }
}
