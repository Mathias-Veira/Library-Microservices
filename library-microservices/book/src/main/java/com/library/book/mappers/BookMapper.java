package com.library.book.mappers;

import com.library.book.dtos.BookDTO;
import com.library.book.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {
    public static BookDTO changeToDTO(Book book){
        return new BookDTO(book.getBookId(), book.getBookName(), book.getBookAuthor(), book.getBookPages(), book.getBookSynopsis(), book.isAvailable());
    }
    public static Book changeToEntity(BookDTO book){
        return new Book(book.getBookId(), book.getBookName(), book.getBookAuthor(), book.getBookPages(), book.getBookSynopsis(), book.isAvailable());
    }
    public static List<BookDTO> changeToListDTO(List<Book> books){
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (Book book: books) {
            bookDTOList.add(changeToDTO(book));
        }
        return bookDTOList;
    }
}
