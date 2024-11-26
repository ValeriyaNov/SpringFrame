package org.example.service;


import org.example.Timer;
import org.example.model.Book;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Optional<Book> getBookById(long id) {

        return bookRepository.findById(id);
    }

    public void deleteBookById(Long id) {

        bookRepository.deleteById(id);
    }
    public Book addBook(Book book) {

        return bookRepository.save(book);
    }

    @Timer
    public Iterable<Book> getAllBook() {

        return bookRepository.findAll();
    }


    public Book updateBook(Long id, Book bookDetails){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            return bookRepository.save(book);
        }
        else{
            throw new IllegalArgumentException("Книга не найдена");
        }
    }
}
