package com.library.loan;

import com.library.loan.dtos.BookDTO;
import com.library.loan.dtos.LoanDTO;
import com.library.loan.dtos.UserDTO;
import com.library.loan.error.ActiveLoanException;
import com.library.loan.mappers.LoanMapper;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private BookClient bookClient;
    @Mock
    private UserClient userClient;
    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    public void shouldThrowActiveLoanException(){
        List<BookDTO> bookDTOList = new ArrayList<>();
        bookDTOList.add(new BookDTO(1,"book test","me",10));
        Loan loan = new Loan(1,1,2, LocalDate.now(),LocalDate.now().plusMonths(1),LocalDate.now());
        when(bookClient.getBooksById(anyList())).thenReturn(bookDTOList);
        when(userClient.findUserById(2)).thenReturn(new UserDTO(2,"userTest","123456","test@gmail.com"));
        when(loanRepository.findActiveLoan(2, 1)).thenReturn(Optional.of(loan));
        assertThrows(ActiveLoanException.class,() ->{ loanService.saveLoan(2,1);});
    }

}
