package com.example.demo.controllers;
import com.example.demo.model.Book;
import com.example.demo.model.Issue;
import com.example.demo.model.Reader;
import com.example.demo.service.IssueService;
import com.example.demo.service.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/readers")
@Tag(name = "Читатели")
public class ReaderController {
    @Autowired
    ReaderService readerService;
    @Autowired
    IssueService issueService;
    @GetMapping("/all")
    @Operation(summary = "Get all readers", description = "Загружает список всех читателей, которые зарегестрированы")
    public ResponseEntity<Iterable<Reader>> getReaderAll(Model model){
        Iterable<Reader> list = readerService.getAllReader();
        model.addAttribute("list", list);
        return list!=null
                ? new ResponseEntity<>(list, HttpStatus.OK)
                : ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get reader by id", description = "Загружает данные читателя с указанным идентификатором в пути")
    public ResponseEntity<Optional<Reader>> getReaderById(@PathVariable long id) {
        Optional<Reader> reader = readerService.getReaderById(id);

        return reader != null
                ? ResponseEntity.ok(reader)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete reader by id", description = "Удаляет данные читателя с указанным идентификатором")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        readerService.deleteReaderById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/reader/{id}/issue")
    public String getIssueById(Model model, @PathVariable Long id) {
        Optional<Reader> reader = readerService.getReaderById(id);
        if (reader == null) {
            throw new NoSuchElementException("читатель не найден");
        }
        List<Issue> issues = issueService.getIssuesByIdReader(id);
        model.addAttribute("id", reader.get().getId());
        model.addAttribute("name", reader.get().getName());
        model.addAttribute("issues", issues);
        return "readerIssue";
    }
    @PostMapping("/reader/")
    @Operation(summary = "Create new reader", description = "Создаёт данные нового читателя в библиотеке")
    public ResponseEntity<Reader> createReader(@RequestBody Reader reader) {
        Reader newReader = readerService.addReader(reader);
        return newReader != null
                ? new ResponseEntity<>(newReader, HttpStatus.CREATED)
                : ResponseEntity.badRequest().build();

    }
    @PutMapping("/{id}")
    @Operation(summary = "Update reader by id", description = "Обновляет данные читателя")
    public ResponseEntity<Reader> updateBookById (@RequestBody Reader initReader, @PathVariable Long id) {
        Optional<Reader> reader = readerService.getReaderById(id);
        if (reader.isPresent()) {

            return new ResponseEntity<>(readerService.updateReader(id, initReader), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
