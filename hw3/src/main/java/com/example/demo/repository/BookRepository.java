package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository {
    private List<Book> list = new ArrayList<>();

    public BookRepository() {
        list.add(new Book("Убить пересмешника"));
        list.add(new Book("Поющие в терновнике"));
        list.add(new Book("Тихий Дон"));
        list.add(new Book("Анна на шее") );
        list.add(new Book("Метель") );

    }
    public List<Book> findAll(){
        return List.copyOf(list);
    }
    public Book findById(long id){

        return list.stream()
                .filter(item -> Objects.equals(item.getId(), id))
                .findFirst()
                .orElse(null);


    }
    public String getDescription(long id) {
        Book book = findById(id);
        if (book != null) {
            return "Название книги : " + book.getName() ;
        } else {
            return "Книга с id " + id + " не найдена.";
        }
    }
    public void delete(long id) {
        list.removeIf(book -> book.getId() == id);
    }
    public Book save(Book book) {
        list.add(book);
        return book;
    }
}
