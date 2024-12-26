package com.uexcel.eazybank.controller;

import com.uexcel.eazybank.dto.LoanDto;
import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.service.ILoanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanController {
    private final ILoanService loanService;
    @GetMapping("/myLoans")
    public ResponseEntity<List<LoanDto>> getLoansDetails(@RequestParam Long accountNumber) {
        List<LoanDto> loans  = loanService.fetchLoanByAccountNumber(accountNumber);
        return ResponseEntity.ok(loans);
    }

    @PostMapping("/addLoan")
    public ResponseEntity<ResponseDto> addLoan(@Valid @RequestBody LoanDto loanDto) {
        ResponseDto res  = loanService.addLoan(loanDto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PutMapping("/updateLoan")
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoanDto loanDto) {
        ResponseDto res  = loanService.updateLoan(loanDto);
        return ResponseEntity.status(res.getStatus()).body(res);
    }
}
