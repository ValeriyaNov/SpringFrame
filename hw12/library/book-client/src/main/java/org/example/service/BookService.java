package org.example.service;


import org.example.model.Book;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {

    public Optional<Book> getBookById(long id);

    public void deleteBookById(Long id) ;
    public Book addBook(Book book) ;

    public Iterable<Book> getAllBook() ;
    public Book addBookWhisDecorator(Book book);


    public Book updateBook(Long id, Book bookDetails);

}
