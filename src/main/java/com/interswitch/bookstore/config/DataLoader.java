package com.interswitch.bookstore.config;

import com.interswitch.bookstore.model.AppUser;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.model.Genre;
import com.interswitch.bookstore.repository.BookRepository;
import com.interswitch.bookstore.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository, UserRepository userRepository) {
        return args -> {

            bookRepository.save(new Book(null, "Things Fall Apart", Genre.FICTION, "978-0-3854-7402-1", "Chinua Achebe", 1958, 2500));
            bookRepository.save(new Book(null, "Gone Girl", Genre.THRILLER, "978-0-3075-8852-9", "Gillian Flynn", 2012, 3200));
            bookRepository.save(new Book(null, "The Girl with the Dragon Tattoo", Genre.MYSTERY, "978-0-3072-6874-3", "Stieg Larsson", 2005, 3500));
            bookRepository.save(new Book(null, "The Shining", Genre.HORROR, "978-0-3851-2171-6", "Stephen King", 1977, 2800));
            bookRepository.save(new Book(null, "Inferno", Genre.THRILLER, "978-0-3855-3753-1", "Dan Brown", 2013, 4000));
            bookRepository.save(new Book(null, "The Raven", Genre.POETRY, "978-0-4862-7002-2", "Edgar Allan Poe", 1845, 1500));
            bookRepository.save(new Book(null, "Pride and Prejudice", Genre.FICTION, "978-0-4864-3775-7", "Jane Austen", 1813, 3000));
            bookRepository.save(new Book(null, "The Picture of Dorian Gray", Genre.SATIRE, "978-0-4862-8087-0", "Oscar Wilde", 1890, 2700));
            bookRepository.save(new Book(null, "It", Genre.HORROR, "978-0-4511-7467-0", "Stephen King", 1986, 5000));
            bookRepository.save(new Book(null, "And Then There Were None", Genre.MYSTERY, "978-0-0620-7381-4", "Agatha Christie", 1939, 3300));

            // === demo user ===
            userRepository.save(new AppUser(null, "Sunday Eze", "sunday@gmail.com", "password123", null, null));
        };
    }

}
