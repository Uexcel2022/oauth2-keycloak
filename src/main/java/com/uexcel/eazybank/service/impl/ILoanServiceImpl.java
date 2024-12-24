package com.uexcel.eazybank.service.impl;

import com.uexcel.eazybank.dto.LoanDto;
import com.uexcel.eazybank.mapper.LoanMapper;
import com.uexcel.eazybank.model.Loans;
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
}
