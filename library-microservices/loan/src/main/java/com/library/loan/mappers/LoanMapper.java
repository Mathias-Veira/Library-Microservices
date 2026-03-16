package com.library.loan.mappers;

import com.library.loan.dtos.LoanDTO;
import com.library.loan.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanMapper {
    public static LoanDTO changeToDTO(Loan loan){
        return new LoanDTO(loan.getLoanId(), loan.getBookId(), loan.getUserId(),loan.getLoanDate(),loan.getLoanExpirationDate(),loan.getLoanReturnDate());
    }

    public static Loan changeToEntity(LoanDTO loan){
        return new Loan(loan.getLoanId(), loan.getBookId(), loan.getUserId(),loan.getLoanDate(),loan.getLoanExpirationDate(),loan.getLoanReturnDate());
    }

    public static List<LoanDTO> changeToListDTO(List<Loan> loans){
        List<LoanDTO> loanDTOList = new ArrayList<>();
        for (Loan loan: loans) {
            loanDTOList.add(changeToDTO(loan));
        }
        return  loanDTOList;
    }
}
