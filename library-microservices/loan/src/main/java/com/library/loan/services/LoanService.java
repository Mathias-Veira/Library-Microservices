package com.library.loan.services;

import com.library.loan.dtos.BookOutOfStockEventDTO;
import com.library.loan.dtos.LoanBookDTO;
import com.library.loan.dtos.LoanDTO;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;


public interface LoanService {
    List<LoanBookDTO> getListLoansByUserId(int userId);
    LoanDTO saveLoan(int userId, int bookId);
    LoanDTO returnBook(int loanId);
    void saveLoanFromReservationEvent(BookOutOfStockEventDTO bookOutOfStockEventDTO);
}
