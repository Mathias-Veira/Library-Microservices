package com.library.loan.repositories;

import com.library.loan.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Integer> {
    @Query("SELECT l FROM Loan l where userId = :userId")
    List<Loan> findLoansByUserId(@Param("userId") int userId);
}
