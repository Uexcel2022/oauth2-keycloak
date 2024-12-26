package com.uexcel.eazybank.service.impl;

import com.uexcel.eazybank.dto.AccountsDto;
import com.uexcel.eazybank.dto.CustomerDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.exceptionhandling.AppExceptionHandler;
import com.uexcel.eazybank.mapper.AccountsMapper;
import com.uexcel.eazybank.model.Accounts;
import com.uexcel.eazybank.model.Customer;
import com.uexcel.eazybank.persistence.AccountsRepository;
import com.uexcel.eazybank.persistence.CustomerRepository;
import com.uexcel.eazybank.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IAccountServiceImpl implements IAccountsService {
    private final AccountsRepository accountsRepository;
    private final AccountsMapper accountsMapper;
    private final CustomerRepository customerRepository;

    /**
     * @param customerId
     * @return - card information using CardDto
     */
    @Override
    public List<AccountsDto> fetchAccountsByCustomerIdOrAccountNumber(Long customerId, Long accountNumber) {
        List<AccountsDto> accountsDtoList = new ArrayList<>();
        List<Accounts> accounts;
        if (customerId != null) {
        accounts = accountsRepository.findByCustomerId(customerId);
            if (accounts.isEmpty()) {
               throw new AppExceptionHandler(HttpStatus.NOT_FOUND.value(),
                        String.format("Account not found for the given " +
                                "input data customerId: %s",customerId)
                );
            }
        }else {
            accounts = accountsRepository.findByAccountNumber(accountNumber);
            if (accounts.isEmpty()) {
                throw new AppExceptionHandler(HttpStatus.NOT_FOUND.value(),
                        String.format("Account not found for the given " +
                                "input data accountNumber: %s",accountNumber)
                );
            }
            AccountsDto ad = accountsMapper.mapToDto(accounts.get(0), new AccountsDto());
            Customer customer = accounts.get(0).getCustomer();
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setName(customer.getName());
            customerDto.setMobileNumber(customer.getMobileNumber());
            customerDto.setEmail(customer.getEmail());
            customerDto.setCreateDt(customer.getCreateDt());
            ad.setCustomer(customerDto);
            accountsDtoList.add(ad);
        }

        accounts.forEach(account ->
                accountsDtoList.add(accountsMapper.mapToDto(account, new AccountsDto()))
        );

        return accountsDtoList;
    }

    @Override
    public ResponseDto createAccount(AccountsDto accountsDto) {
        if(accountsDto == null){
            throw new AppExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    String.format("Input data is null")
            );
        }
        String mobileNumber = accountsDto.getCustomer().getMobileNumber();
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new AppExceptionHandler(HttpStatus.NOT_FOUND.value(),
                String.format("Account not found for the given " +
                        "input data accountNumber: %s",mobileNumber)
        ));

        Accounts accounts = accountsMapper.toAccount(new Accounts(),accountsDto);
        accounts.setCustomer(customer);
         Accounts savedAccount = accountsRepository.save(accounts);
         if(savedAccount != null){
             return new ResponseDto(HttpStatus.CREATED.value(),
                     HttpStatus.CREATED,"Account created successfully");
         }
        throw new AppExceptionHandler(HttpStatus.EXPECTATION_FAILED.value(),"Account creation failed");
    }
}
