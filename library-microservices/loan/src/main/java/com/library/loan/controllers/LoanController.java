package com.library.loan.controllers;

import com.library.loan.dtos.ErrorDTO;
import com.library.loan.dtos.LoanBookDTO;
import com.library.loan.dtos.LoanDTO;
import com.library.loan.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LoanController {
    private final LoanService loanService;
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<LoanBookDTO>> getLoansByUserId(@PathVariable int userId){
        return new ResponseEntity<>(loanService.getListLoansByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/save_loan")
    public ResponseEntity<?> saveLoan(@RequestParam int userId, int bookId){
        LoanDTO loanDTO = loanService.saveLoan(userId, bookId);
        if(loanDTO == null){
            return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST,"No stock for this book, loan cancelled"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(loanDTO,HttpStatus.OK);
    }

    @PostMapping("/return_book")
    public ResponseEntity<LoanDTO> returnLoan(@RequestParam int loanId){
        return new ResponseEntity<>(loanService.returnBook(loanId),HttpStatus.OK);
    }
}
