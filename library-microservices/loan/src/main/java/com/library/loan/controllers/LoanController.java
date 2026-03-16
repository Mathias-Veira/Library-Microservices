package com.library.loan.controllers;

import com.library.loan.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoanController {
    private final LoanService loanService;
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getLoansByUserId(@PathVariable int userId){
        return new ResponseEntity<>(loanService.getListLoansByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/save_loan")
    public ResponseEntity<?> saveLoan(@RequestParam int userId, int bookId){
        return new ResponseEntity<>(loanService.saveLoan(userId, bookId),HttpStatus.OK);
    }
}
