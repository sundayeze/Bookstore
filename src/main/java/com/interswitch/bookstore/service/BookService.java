package com.interswitch.bookstore.service;

import com.interswitch.bookstore.exceptions.BookNotFoundException;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.model.Genre;
import com.interswitch.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> searchBooks(String title, String author, Integer year, Genre genre) {
        if (title != null) {
            return searchByTitle(title);
        } else if (author != null) {
            return searchByAuthor(author);
        } else if (year != null) {
            return searchByYear(year);
        } else if (genre != null) {
            return searchByGenre(genre);
        }

        throw new BookNotFoundException(" -- No books found with this search.");
    }

    public List<Book> searchByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException("-- No books found with title: " + title);
        }
        return books;
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException(" -- No books found by author: " + author);
        }
        return books;
    }

    public List<Book> searchByYear(Integer year) {
        List<Book> books = bookRepository.findByPublicationYear(year);
        if (books.isEmpty()) {
            throw new BookNotFoundException("-- No books found from the year: " + year);
        }
        return books;
    }

    public List<Book> searchByGenre(Genre genre) {
        List<Book> books = bookRepository.findByGenre(genre);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found in genre: " + genre);
        }
        return books;
    }


    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books available in store");
        }
        return books;
    }

}
