package com.uexcel.eazybank.service;

import com.uexcel.eazybank.dto.CreateCustomerDto;
import com.uexcel.eazybank.dto.CustomerResponseDto;
import com.uexcel.eazybank.dto.ResponseDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface ICustomerService {

    ResponseDto addCustomer(CreateCustomerDto createCustomerDto);

    CustomerResponseDto getCustomerDetails(String email);

    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(new Date());
    }
}
