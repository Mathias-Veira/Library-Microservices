package com.library.book.repositories;

import com.library.book.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query("SELECT b FROM Book b where b.available")
    List<Book> findBooksAvailable();
    @Query("SELECT b FROM Book b where b.bookName like %:bookName%")
    List<Book> findBooksByName(@Param("bookName") String bookName);
    @Query("SELECT b FROM Book b where b.bookAuthor like %:bookAuthor%")
    List<Book> findBooksByAuthor(@Param("bookAuthor") String bookAuthor);
    @Query("SELECT b FROM Book b where b.bookId in :bookId")
    List<Book> findBooksById(@Param("bookId") List<Integer> booksIds);
}
