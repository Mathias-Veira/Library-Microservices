package com.library.book.services.impl;
import com.library.book.models.Book;
import com.library.book.dtos.BookDTO;
import com.library.book.dtos.BookOutOfStockEventDTO;
import com.library.book.mappers.BookMapper;
import com.library.book.repositories.BookRepository;
import com.library.book.services.BookService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> findAllBooks() {
        return BookMapper.changeToListDTO(bookRepository.findAll());
    }

    @Override
    public List<BookDTO> findBooksByName(String bookName) {
        return BookMapper.changeToListDTO(bookRepository.findBooksByName(bookName));
    }

    @Override
    public List<BookDTO> findBooksByAuthor(String bookAuthor) {
        return BookMapper.changeToListDTO(bookRepository.findBooksByAuthor(bookAuthor));
    }

    @Override
    public List<BookDTO> findBookById(List<Integer> bookId) {
        return BookMapper.changeToListDTO(bookRepository.findBooksById(bookId));
    }
    @KafkaListener(topics = "book_returned", groupId = "book")
    @Override
    public BookDTO addBookStock(BookOutOfStockEventDTO bookOutOfStockEventDTO) {
        Optional<Book> bookOptional = bookRepository.findById(bookOutOfStockEventDTO.getBookId());
        Book book;
        if(bookOptional.isEmpty()){
            return null;
        }
        book = bookOptional.get();
        book.setStock(book.getStock()+1);
        return BookMapper.changeToDTO(bookRepository.save(book));
    }
    @KafkaListener(topics = "loan_created", groupId = "book")
    @Override
    public void removeBookStock(BookOutOfStockEventDTO bookOutOfStockEventDTO) {
        Optional<Book> bookOptional = bookRepository.findById(bookOutOfStockEventDTO.getBookId());
        Book book;
        if(bookOptional.isPresent()){
            book = bookOptional.get();
            book.setStock(book.getStock()-1);
            bookRepository.save(book);
        }
    }
}
