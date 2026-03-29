package com.library.loan.services.impl;

import com.library.loan.dtos.BookDTO;
import com.library.loan.dtos.LoanBookDTO;
import com.library.loan.dtos.LoanDTO;
import com.library.loan.error.IdNotFoundException;
import com.library.loan.mappers.LoanMapper;
import com.library.loan.models.Loan;
import com.library.loan.repositories.LoanRepository;
import com.library.loan.services.BookClient;
import com.library.loan.services.LoanService;
import com.library.loan.services.UserClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final BookClient bookClient;
    private final UserClient userClient;
    public LoanServiceImpl(LoanRepository loanRepository,BookClient bookClient, UserClient userClient){
        this.loanRepository = loanRepository;
        this.bookClient = bookClient;
        this.userClient = userClient;
    }

    @Override
    public List<LoanBookDTO> getListLoansByUserId(int userId) {
        userClient.findUserById(userId);
        //stores the loan and book data
        List<LoanBookDTO> loanBookDTOList = new ArrayList<>();
        //stores all the loans from a user
        List<LoanDTO> loanDTOList =  LoanMapper.changeToListDTO(loanRepository.findLoansByUserId(userId));
        //stores books ids from the loans
        List<Integer> booksIds = loanDTOList.stream().map(LoanDTO::getBookId).distinct().collect(Collectors.toList());
        //stores book info
        List<BookDTO> bookDTOList = bookClient.getBooksById(booksIds);
        //store book info in a map
        Map<Integer,BookDTO> bookDTOMap = bookDTOList.stream().collect(Collectors.toMap(BookDTO::getBookId,bookDTO -> bookDTO));
        //add books info into response object
        for (LoanDTO loanDTO : loanDTOList) {
            loanBookDTOList.add(new LoanBookDTO(loanDTO.getLoanId(), bookDTOMap.get(loanDTO.getBookId()), loanDTO.getUserId(), loanDTO.getLoanDate(), loanDTO.getLoanExpirationDate(), loanDTO.getLoanReturnDate()));
        }
        return loanBookDTOList;
    }

    @Override
    public LoanDTO saveLoan(int userId, int bookId) {
        if(userClient.findUserById(userId) == null || bookClient.getBooksById(new ArrayList<>(List.of(bookId))).isEmpty()){
            throw new IdNotFoundException("The user or the book does not exist");
        }
        return LoanMapper.changeToDTO(loanRepository.save(new Loan(0,bookId,userId, LocalDate.now(),LocalDate.now().plusMonths(1),null)));
    }
}
