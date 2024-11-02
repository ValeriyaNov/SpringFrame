package com.example.demo.controllers;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;
    @GetMapping("/all")
    public String getBookAll(Model model){
        Iterable<Book> list = bookService.getAllBook();
        model.addAttribute("list", list);

        return "books";
    }

    @PostMapping("/book/")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return newBook != null
                ? new ResponseEntity<>(newBook, HttpStatus.CREATED)//ResponseEntity.ok(newBook)
                : ResponseEntity.badRequest().build();

    }
    @GetMapping("/{id}")
    public String getBookInfo(Model model, @PathVariable Long id) {
        Optional<Book> list = bookService.getBookById(id);
        model.addAttribute("list", list.get());
        if (list != null) {
            return "books";
        } else {
            throw new IllegalArgumentException("Книга не найдена");

        }
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
            bookService.deleteBookById(id);
            return ResponseEntity.noContent().build();
        }
    }



