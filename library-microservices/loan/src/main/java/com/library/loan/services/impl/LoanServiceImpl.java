package com.library.loan.services.impl;

import com.library.loan.dtos.BookDTO;
import com.library.loan.dtos.BookOutOfStockEventDTO;
import com.library.loan.dtos.LoanBookDTO;
import com.library.loan.dtos.LoanDTO;
import com.library.loan.error.IdNotFoundException;
import com.library.loan.mappers.LoanMapper;
import com.library.loan.models.Loan;
import com.library.loan.repositories.LoanRepository;
import com.library.loan.services.BookClient;
import com.library.loan.services.LoanService;
import com.library.loan.services.UserClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final BookClient bookClient;
    private final UserClient userClient;
    private final KafkaTemplate<String, BookOutOfStockEventDTO> kafkaTemplate;

    public LoanServiceImpl(LoanRepository loanRepository, BookClient bookClient, UserClient userClient, KafkaTemplate<String, BookOutOfStockEventDTO> kafkaTemplate) {
        this.loanRepository = loanRepository;
        this.bookClient = bookClient;
        this.userClient = userClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public List<LoanBookDTO> getListLoansByUserId(int userId) {
        userClient.findUserById(userId);
        //stores the loan and book data
        List<LoanBookDTO> loanBookDTOList = new ArrayList<>();
        //stores all the loans from a user
        List<LoanDTO> loanDTOList = LoanMapper.changeToListDTO(loanRepository.findLoansByUserId(userId));
        //stores books ids from the loans
        List<Integer> booksIds = loanDTOList.stream().map(LoanDTO::getBookId).distinct().collect(Collectors.toList());
        //stores book info
        List<BookDTO> bookDTOList = bookClient.getBooksById(booksIds);
        //store book info in a map
        Map<Integer, BookDTO> bookDTOMap = bookDTOList.stream().collect(Collectors.toMap(BookDTO::getBookId, bookDTO -> bookDTO));
        //add books info into response object
        for (LoanDTO loanDTO : loanDTOList) {
            loanBookDTOList.add(new LoanBookDTO(loanDTO.getLoanId(), bookDTOMap.get(loanDTO.getBookId()), loanDTO.getUserId(), loanDTO.getLoanDate(), loanDTO.getLoanExpirationDate(), loanDTO.getLoanReturnDate()));
        }
        return loanBookDTOList;
    }

    @Override
    public LoanDTO saveLoan(int userId, int bookId) {
        List<BookDTO> bookDTOList = bookClient.getBooksById(new ArrayList<>(List.of(bookId)));
        if (userClient.findUserById(userId) == null || bookDTOList.isEmpty()) {
            throw new IdNotFoundException("The user or the book does not exist");
        }
        BookDTO bookDTO = bookDTOList.get(0);
        if (bookDTO.getStock() <= 0) {
            kafkaTemplate.send("book_out_of_stock", new BookOutOfStockEventDTO(userId, bookId));
            return null;
        }
        kafkaTemplate.send("loan_created", new BookOutOfStockEventDTO(userId,bookId));

        return LoanMapper.changeToDTO(loanRepository.save(new Loan(0, bookId, userId, LocalDate.now(), LocalDate.now().plusMonths(1), null)));
    }

    @KafkaListener(topics = "reservation_ready", groupId = "loan")
    @Override
    public void saveLoanFromReservationEvent(BookOutOfStockEventDTO bookOutOfStockEventDTO) {
        loanRepository.save(new Loan(0, bookOutOfStockEventDTO.getBookId(), bookOutOfStockEventDTO.getUserId(), LocalDate.now(), LocalDate.now().plusMonths(1), null));
    }

    @Override
    public LoanDTO returnBook(int loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new IdNotFoundException("The loan does not exist"));
        loan.setLoanReturnDate(LocalDate.now());
        kafkaTemplate.send("book_returned", new BookOutOfStockEventDTO(loan.getUserId(), loan.getBookId()));
        return LoanMapper.changeToDTO(loanRepository.save(loan));
    }
}
