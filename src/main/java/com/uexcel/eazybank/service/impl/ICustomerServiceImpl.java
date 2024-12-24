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
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ICustomerServiceImpl implements ICustomerService {
    private final IAccountsService accountsService;
    private final ICardService cardService;
    private final ILoanService loanService;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    private CompromisedPasswordChecker cPC;
    @Override
    public ResponseDto addCustomer(CreateCustomerDto createCustomerDto) {
        if(cPC.check(createCustomerDto.getPwd()).isCompromised()){
            throw new AppExceptionHandler(
                    HttpStatus.BAD_REQUEST.value(),
                    String.format("Password %s is considered compromised.", createCustomerDto.getPwd()));
        }
        String hashedPassword = passwordEncoder.encode(createCustomerDto.getPwd());
        createCustomerDto.setPwd(hashedPassword);
        Customer customer = customerRepository
                .save(customerMapper.toCustomer(new Customer(), createCustomerDto));

       if(customer.getId() > 0){
           return new ResponseDto(HttpStatus.CREATED.value(),
                   HttpStatus.CREATED,"Customer registered successfully.");
       }
        throw new AppExceptionHandler(
                HttpStatus.EXPECTATION_FAILED.value(),"Registration failed.");
    }

    /**
     * @param mobileNumber
     * @return - will hold customer consolidated info
     */
    @Override
    public CustomerResponseDto getCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new AppExceptionHandler(HttpStatus.NOT_FOUND.value(),
                                String.format("Customer not found for the given " +
                                        "input data mobileNumber: %s",mobileNumber)
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
