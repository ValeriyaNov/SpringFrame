package com.example.demo.service;

import com.example.demo.aspect.RecoverException;
import com.example.demo.aspect.Timer;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import jakarta.transaction.Transactional;
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
