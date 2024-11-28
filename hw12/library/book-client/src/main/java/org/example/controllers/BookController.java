package org.example.controllers;
import org.example.model.Book;
import org.example.model.Ebook;
import org.example.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.EbookService;
import org.example.service.FileGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Tag(name = "Книги")
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    EbookService ebookService;
    private final FileGateway fileGateway;

    public BookController(FileGateway fileGateway) {
        this.fileGateway = fileGateway;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all books", description = "Загружает список всех книг, которые есть в библиотеке")
    public ResponseEntity<Iterable<Book>> getBookAll(){
        Iterable<Book> list = bookService.getAllBook();
        return list!=null
                ? ResponseEntity.ok(list)
                : ResponseEntity.notFound().build();
    }
    @GetMapping("/allEl")
    @Operation(summary = "Get all books", description = "Загружает список всех книг, которые есть в библиотеке")
    public ResponseEntity<Iterable<Ebook>> getBookAllEl(){
        Iterable<Ebook> list = ebookService.getAllBook();
        return list!=null
                ? ResponseEntity.ok(list)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/book/")
    @Operation(summary = "Create new book", description = "Создаёт новую книгу в библиотеке")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        fileGateway.writeToFile(newBook.getTitle() + ".txt", newBook.toString());
        return newBook != null
                ? new ResponseEntity<>(newBook, HttpStatus.CREATED)
                : ResponseEntity.badRequest().build();

    }
    @PostMapping("/bookEl/")
    @Operation(summary = "Create new book", description = "Создаёт новую книгу в библиотеке, если есть ее электронная версия")
    public ResponseEntity<Book> createBookEl(@RequestBody Book book) {
        Book newBook = bookService.addBookWhisDecorator(book);
        return newBook != null
                ? new ResponseEntity<>(newBook, HttpStatus.CREATED)
                : ResponseEntity.badRequest().build();

    }
    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Загружает книгу с указанным идентификатором в пути")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable long id) {
        Optional<Book> book = bookService.getBookById(id);

        return book != null
                ? ResponseEntity.ok(book)
                : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by id", description = "Удаляет книгу с указанным идентификатором из библиотеки")
        public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
            bookService.deleteBookById(id);
            return ResponseEntity.noContent().build();
        }

    @PutMapping("/{id}")
    @Operation(summary = "Update book by id", description = "Обновляет данные книги")
    public ResponseEntity<Book> updateBookById (@RequestBody Book initBook, @PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {

            return new ResponseEntity<>(bookService.updateBook(id, initBook), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    }



