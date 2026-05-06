package com.library.loan;

import com.library.loan.dtos.BookDTO;
import com.library.loan.dtos.BookEventDTO;
import com.library.loan.dtos.LoanDTO;
import com.library.loan.dtos.UserDTO;
import com.library.loan.error.ActiveLoanException;
import com.library.loan.error.IdNotFoundException;
import com.library.loan.models.Loan;
import com.library.loan.repositories.LoanRepository;
import com.library.loan.services.BookClient;
import com.library.loan.services.UserClient;
import com.library.loan.services.impl.LoanServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private BookClient bookClient;
    @Mock
    private UserClient userClient;
    @Mock
    private KafkaTemplate<String, BookEventDTO> kafkaTemplate;
    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
     void shouldThrowActiveLoanException(){
        List<BookDTO> bookDTOList = new ArrayList<>();
        bookDTOList.add(new BookDTO(1,"book test","me",10));
        Loan loan = new Loan(1,1,2, LocalDate.now(),LocalDate.now().plusMonths(1),LocalDate.now());
        when(bookClient.getBooksById(anyList())).thenReturn(bookDTOList);
        when(userClient.findUserById(2)).thenReturn(new UserDTO(2,"userTest","123456","test@gmail.com"));
        when(loanRepository.findActiveLoan(2, 1)).thenReturn(Optional.of(loan));
        assertThrows(ActiveLoanException.class,() ->{ loanService.saveLoan(2,1);});
    }

    @Test
     void shouldCreateNewLoan(){
        int userId = 2;
        int bookId = 1;
        BookDTO bookDTO = new BookDTO(bookId,"book test","me",10);
        UserDTO userDTO = new UserDTO(userId,"userTest","123456","test@gmail.com");
        Loan loan = new Loan(1,bookId,userId, LocalDate.now(),LocalDate.now().plusMonths(1),LocalDate.now());
        when(bookClient.getBooksById(anyList())).thenReturn(List.of(bookDTO));
        when(userClient.findUserById(userId)).thenReturn(userDTO);
        when(loanRepository.findActiveLoan(userId, bookId)).thenReturn(Optional.empty());
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        LoanDTO loanDTO = loanService.saveLoan(userId, bookId);
        verify(kafkaTemplate).send(eq("loan_created"),any(BookEventDTO.class));
        assertEquals(loan.getLoanId(),loanDTO.getLoanId());
    }

    @Test
     void shouldPublishBookOutOfStockEvent(){
        int userId = 2;
        int bookId = 1;
        BookDTO bookDTO = new BookDTO(bookId,"book test","me",0);
        UserDTO userDTO = new UserDTO(userId,"userTest","123456","test@gmail.com");
        when(bookClient.getBooksById(anyList())).thenReturn(List.of(bookDTO));
        when(userClient.findUserById(userId)).thenReturn(userDTO);
        LoanDTO loanDTO = loanService.saveLoan(userId,bookId);
        verify(kafkaTemplate).send(eq("book_out_of_stock"),any(BookEventDTO.class));
        verify(loanRepository, never()).save(any());
        assertNull(loanDTO);
    }

    @Test
     void shouldThrowIdNotFoundException(){
        int loanId = 99;
        assertThrows(IdNotFoundException.class,() ->{ loanService.returnBook(loanId);});
    }

    @Test
     void shouldReturnBookReturned(){
        int loanId = 1;
        int bookId = 1;
        int userId = 2;
        Loan loan = new Loan(loanId,bookId,userId, LocalDate.now(),LocalDate.now().plusMonths(1),null);
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        LoanDTO loanDTO = loanService.returnBook(loanId);
        assertNotNull(loanDTO.getLoanReturnDate());
        assertEquals(LocalDate.now(),loanDTO.getLoanReturnDate());
        verify(kafkaTemplate).send(eq("book_returned"),any(BookEventDTO.class));
    }

}
