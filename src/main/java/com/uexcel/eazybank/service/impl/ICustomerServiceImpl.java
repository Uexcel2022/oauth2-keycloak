package com.uexcel.eazybank.service.impl;

import com.uexcel.eazybank.dto.*;
import com.uexcel.eazybank.exceptionhandling.AppExceptionHandler;
import com.uexcel.eazybank.mapper.CustomerMapper;
import com.uexcel.eazybank.model.Customer;
import com.uexcel.eazybank.persistence.CustomerRepository;
import com.uexcel.eazybank.service.IAccountsService;
import com.uexcel.eazybank.service.ICardService;
import com.uexcel.eazybank.service.ICustomerService;
import com.uexcel.eazybank.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ICustomerServiceImpl implements ICustomerService {
    private final IAccountsService accountsService;
    private final ICardService cardService;
    private final ILoanService loanService;
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    public ResponseDto addCustomer(CreateCustomerDto createCustomerDto) {

        if(customerRepository.existsByEmail(createCustomerDto.getEmail())) {
            return new ResponseDto(HttpStatus.FOUND.value(),
                    HttpStatus.FOUND,String.format("There is a customer with the email: %s",
                    createCustomerDto.getEmail()));
        }
        if(customerRepository.existsByMobileNumber(createCustomerDto.getMobileNumber())) {
            return new ResponseDto(HttpStatus.FOUND.value(),
                    HttpStatus.FOUND,String.format("There is a customer with the phone number: %s",
                    createCustomerDto.getMobileNumber()));
        }

        Customer customer = customerRepository
                .save(customerMapper.toCustomer(new Customer(), createCustomerDto));

       if(customer.getId()>0){
           return new ResponseDto(HttpStatus.CREATED.value(),
                   HttpStatus.CREATED,"Customer registered successfully.");
       }
        throw new AppExceptionHandler(
                HttpStatus.EXPECTATION_FAILED.value(),"Registration failed.");
    }

    /**
     * @param email
     * @return - will hold customer consolidated info
     */
    @Override
    @PreAuthorize("hasRole('USER')")
    public CustomerResponseDto getCustomerDetails(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()-> new AppExceptionHandler(HttpStatus.NOT_FOUND.value(),
                                String.format("Customer not found for the given " +
                                        "input data mobileNumber: %s",email)
                        )
                );
        CustomerResponseDto customerResponseDto =
                customerMapper.toDto(new CustomerResponseDto(),customer);

        List<AccountsDto> accountsDtoList =
                accountsService.fetchAccountsByCustomerIdOrAccountNumber(customer.getId(), null);
            customerResponseDto.setAccounts(accountsDtoList);


        accountsDtoList.forEach(account ->{
        List<LoanDto> loanDtoList = loanService.fetchLoanByAccountNumber(account.getAccountNumber());
            if(!loanDtoList.isEmpty()) {
                customerResponseDto.getLoans().addAll(loanDtoList);
            }
        });

        accountsDtoList.forEach(account ->{
            List<CardsDto> cardDtoList = cardService.fetchCardsByAccountNumber(account.getAccountNumber());
            if(!cardDtoList.isEmpty()) {
                customerResponseDto.getCards().addAll(cardDtoList);
            }
        });

        return customerResponseDto;
    }
}
