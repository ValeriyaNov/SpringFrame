package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    //@Query("SELECT * FROM books WHERE title = :title")
    //List<Book> findBookByTitle(String title);

   // @Modifying
   // @Query("UPDATE books SET book = :book WHERE id= :id")
   // void changeBook(Long id, String title);

}
