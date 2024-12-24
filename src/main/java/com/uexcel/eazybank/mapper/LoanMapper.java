package com.uexcel.eazybank.mapper;

import com.uexcel.eazybank.dto.LoanDto;
import com.uexcel.eazybank.model.Loans;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanMapper {
    public LoanDto toLoanDto(Loans loans, LoanDto loanDto){
        loanDto.setId(loans.getLoanNumber());
        loanDto.setLoanType(loans.getLoanType());
        loanDto.setTotalLoan(loans.getTotalLoan());
        loanDto.setAmountPaid(loans.getAmountPaid());
        loanDto.setOutstandingAmount(loans.getOutstandingAmount());
        loanDto.setCreateDt(loans.getCreateDt());
        loanDto.setStartDt(loans.getStartDt());
        loanDto.setAccountNumber(loans.getAccounts().getAccountNumber());
        return loanDto;
    }

    public Loans toLoans(Loans loans,LoanDto loanDto){
        loans.setLoanType(loanDto.getLoanType());
        loans.setTotalLoan(loanDto.getTotalLoan());
        loans.setAmountPaid(loanDto.getAmountPaid());
        loans.setOutstandingAmount(loanDto.getOutstandingAmount());
        loans.setCreateDt(LocalDate.now());
        loans.setStartDt(loanDto.getStartDt());
        return loans;
    }
}
