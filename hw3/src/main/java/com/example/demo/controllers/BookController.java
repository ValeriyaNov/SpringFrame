package com.example.demo.controllers;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;
    @GetMapping("/all")
    public String getBookAll(Model model){
        List<Book> list = bookService.getAllBook();
        model.addAttribute("list", list);
        //String listBook = "Книги:";
        //for (int i = 0; i<list.size()-1; i++){
         //   listBook = listBook + list.get(i).getName() + "; ";
        //}
        return "books";
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
