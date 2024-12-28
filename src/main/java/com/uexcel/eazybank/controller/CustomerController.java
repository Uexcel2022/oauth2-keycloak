package com.uexcel.eazybank.controller;

import com.uexcel.eazybank.dto.CreateCustomerDto;
import com.uexcel.eazybank.dto.CustomerResponseDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Validated
public class CustomerController {
    private final ICustomerService iCustomerService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> addCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
        ResponseDto rs = iCustomerService.addCustomer(createCustomerDto);
        return ResponseEntity.status(rs.getStatus()).body(rs);
    }
    @GetMapping("/fetch-customer")
    public ResponseEntity<CustomerResponseDto> getCustomerDetails(@RequestParam String mobileNumber) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        log.error("*****************{}***************",a.getName());
        CustomerResponseDto cRD = iCustomerService.getCustomerDetails(mobileNumber);
        return ResponseEntity.ok(cRD);
    }
}
