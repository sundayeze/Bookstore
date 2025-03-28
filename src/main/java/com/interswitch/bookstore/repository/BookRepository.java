package com.interswitch.bookstore.repository;

import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByPublicationYear(int year);
    List<Book> findByGenre(Genre genre);

}
