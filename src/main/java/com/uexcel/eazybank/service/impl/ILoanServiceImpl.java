package com.uexcel.eazybank.service.impl;

import com.uexcel.eazybank.dto.LoanDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.exceptionhandling.AppExceptionHandler;
import com.uexcel.eazybank.mapper.LoanMapper;
import com.uexcel.eazybank.model.Accounts;
import com.uexcel.eazybank.model.Loans;
import com.uexcel.eazybank.persistence.AccountsRepository;
import com.uexcel.eazybank.persistence.LoanRepository;
import com.uexcel.eazybank.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ILoanServiceImpl implements ILoanService {
    private final LoanRepository loanRepository;
    private final AccountsRepository accountsRepository;
    private final LoanMapper loanMapper;
    /**
     * @param accountNumber
     * @return
     */
    @Override
    public List<LoanDto> fetchLoanByAccountNumber(Long accountNumber) {
        List<Loans> loans = loanRepository.findByAccounts_AccountNumber(accountNumber);
        List<LoanDto> loansDtoList = new ArrayList<>();
        if (loans.isEmpty()) {
            LoanDto loanDto = new LoanDto();
            loanDto.setStatus(HttpStatus.NOT_FOUND.value());
            loanDto.setMessage("There is no loan associated with accountNumber: " + accountNumber);
            loansDtoList.add(loanDto);
            return loansDtoList;
        }
        loans.forEach(loan -> loansDtoList.add(loanMapper.toLoanDto(loan, new LoanDto())));

        return loansDtoList;
    }

    @Override
    public ResponseDto addLoan(LoanDto loanDto) {
        Accounts account = accountsRepository.findById(loanDto.getAccountNumber())
                .orElseThrow(()->
                        new AppExceptionHandler(HttpStatus.NOT_FOUND.value(),
                                "No account found for accountNumber: " + loanDto.getAccountNumber() ));
      Loans loan =  loanMapper.toLoans(new Loans(),loanDto);
      loan.setAccounts(account);
         Loans savedLoan = loanRepository.save(loan);

        if(savedLoan.getLoanNumber()>0){
            return new ResponseDto(HttpStatus.CREATED.value(),
                    HttpStatus.CREATED,"Loam created successfully.");
        }
        throw new AppExceptionHandler(
                HttpStatus.EXPECTATION_FAILED.value(),"Loan creation failed.");
    }

    @Override
    public ResponseDto updateLoan(LoanDto loanDto) {
        if(loanDto.getId()==null){
            throw new AppExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    "The loan number is null." );
        }

        Accounts account = accountsRepository.findById(loanDto.getAccountNumber())
                .orElseThrow(()->
                        new AppExceptionHandler(HttpStatus.NOT_FOUND.value(),
                                "No account found for accountNumber: " + loanDto.getAccountNumber() ));
        Loans loan =  loanMapper.toLoans(new Loans(),loanDto);
        loan.setLoanNumber(loanDto.getId());
        loan.setAccounts(account);
        Loans savedLoan = loanRepository.save(loan);

        if(savedLoan.getLoanNumber()>0){
            return new ResponseDto(HttpStatus.OK.value(),
                    HttpStatus.OK,"Loam updated successfully.");
        }
        throw new AppExceptionHandler(
                HttpStatus.EXPECTATION_FAILED.value(),"Loan update failed.");
    }


}
