package com.library.loan.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Loan")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private int loanId;
    @Column(name = "book_id")
    private int bookId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "loan_date")
    private LocalDate loanDate;
    @Column(name = "loan_expiration_date")
    private LocalDate loanExpirationDate;
    @Column(name = "loan_return_date")
    private LocalDate loanReturnDate;
}
