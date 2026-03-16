package com.library.loan.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanDTO {
    private int loanId;
    private int bookId;
    private int userId;
    private LocalDate loanDate;
    private LocalDate loanExpirationDate;
    private LocalDate loanReturnDate;
}
