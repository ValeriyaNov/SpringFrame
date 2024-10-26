package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book getBookById(long id) {

        return bookRepository.findById(id);
    }

    public void deleteBookById(Long id) {

        bookRepository.delete(id);
    }
    public Book addBook(Book book) {

        return bookRepository.save(book);
    }


    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }
}
