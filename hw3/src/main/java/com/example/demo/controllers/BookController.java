package com.example.demo.controllers;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;
    @GetMapping("/all")
    public String getBookAll(){
        List<Book> list = bookService.getAllBook();
        String listBook = "Книги:";
        for (int i = 0; i<list.size()-1; i++){
            listBook = listBook + list.get(i).getName() + "; ";
        }
        return listBook + list.get(list.size()-1).getName() + ".";
    }

    @PostMapping("/book/{name}")
    public Book createBook(@PathVariable String name) {
        Book newBook = new Book(name);
        bookService.addBook(newBook);
        return newBook;
    }
    @GetMapping("{id}")
    public String getBookInfo(@PathVariable Long id) {
        Book book = bookService.getBookById(id);

        if (book != null) {
            return book.getName();
        } else {
            return "Книга с id = " + id + " не найдена";

        }
    }

    @DeleteMapping("{id}")
    public String deleteReader(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            bookService.deleteBookById(id);
            return "Книга с id=" + id + " удалена";
        } else {
            return "Книга с id=" + id + " не найдена";
        }
    }


}
