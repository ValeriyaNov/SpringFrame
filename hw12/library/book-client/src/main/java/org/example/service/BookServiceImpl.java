package org.example.service;


import org.example.model.Book;
import org.example.model.Ebook;
import org.example.repository.BookRepository;
import org.example.repository.EbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EbookRepository ebookRepository;
    private static BookServiceImpl instance;
    private BookServiceImpl(){};
    public static BookServiceImpl getInstance(){
        if(instance==null){
            instance = new BookServiceImpl();
        }
        return instance;
    }
    @Override
    public Optional<Book> getBookById(long id) {

        return bookRepository.findById(id);
    }
    @Override
    public void deleteBookById(Long id) {

        bookRepository.deleteById(id);
    }
    @Override
    public Book addBook(Book book) {

        return bookRepository.save(book);
    }

    @Override
    public Iterable<Book> getAllBook() {

        return bookRepository.findAll();
    }

    @Override
    public Book addBookWhisDecorator(Book book) {
        Book newBook = bookRepository.save(book);
        Ebook ebook = new Ebook();
        ebook.setBook(book);
        ebookRepository.save(ebook);
        return newBook;

    }

    @Override
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
