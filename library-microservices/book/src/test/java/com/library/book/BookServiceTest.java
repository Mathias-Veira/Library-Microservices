package com.library.book;


import com.library.book.models.Book;
import com.library.book.repositories.BookRepository;
import com.library.book.services.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;
    @Test
    void shouldReturnBookByAuthor(){
        String author = "Brandon Sanderson";
        List<Book> books = new ArrayList<>();
        books.add(new Book(1,"The Way of Kings","Brandon Sanderson",1190,"",10));
        when(bookRepository.findBooksByAuthor(author)).thenReturn(books);
        assertNotNull(bookService.findBooksByAuthor(author));
        verify(bookRepository).findBooksByAuthor(author);
        assertEquals(1,books.size());
        assertEquals(author,books.get(0).getBookAuthor());
    }

}
