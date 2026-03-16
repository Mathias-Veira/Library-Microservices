package com.library.loan.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanBookDTO {
    private int loanId;
    private BookDTO book;
    private int userId;
    private LocalDate loanDate;
    private LocalDate loanExpirationDate;
    private LocalDate loanReturnDate;
}
