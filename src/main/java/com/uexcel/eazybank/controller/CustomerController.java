package com.uexcel.eazybank.controller;

import com.uexcel.eazybank.dto.CreateCustomerDto;
import com.uexcel.eazybank.dto.CustomerResponseDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CustomerController {
    private final ICustomerService iCustomerService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> addCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        ResponseDto rs = iCustomerService.addCustomer(createCustomerDto);
        return ResponseEntity.status(rs.getStatus()).body(rs);
    }
    @GetMapping("fetch-customer")
    public ResponseEntity<CustomerResponseDto> getCustomerDetails(@RequestParam String mobileNumber) {
        CustomerResponseDto cRD = iCustomerService.getCustomerDetails(mobileNumber);
        return ResponseEntity.ok(cRD);
    }
}
