package com.uexcel.eazybank.mapper;

import com.uexcel.eazybank.dto.CreateCustomerDto;
import com.uexcel.eazybank.dto.CustomerResponseDto;
import com.uexcel.eazybank.model.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerMapper {
    public Customer toCustomer(Customer customer, CreateCustomerDto createCustomerDto) {
        customer.setEmail(createCustomerDto.getEmail());
        customer.setName(createCustomerDto.getName());
        customer.setMobileNumber(createCustomerDto.getMobileNumber());
        customer.setCreateDt(LocalDate.now());
        return customer;
    }

    public CustomerResponseDto toDto(CustomerResponseDto cRD, Customer customer) {
        cRD.setId(customer.getId());
        cRD.setName(customer.getName());
        cRD.setMobileNumber(customer.getMobileNumber());
        cRD.setEmail(customer.getEmail());
        cRD.setCreateDt(customer.getCreateDt());
        return cRD;
    }
}
