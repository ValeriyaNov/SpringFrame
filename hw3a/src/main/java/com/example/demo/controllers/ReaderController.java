package com.example.demo.controllers;
import com.example.demo.model.Book;
import com.example.demo.model.Issue;
import com.example.demo.model.Reader;
import com.example.demo.service.IssueService;
import com.example.demo.service.ReaderService;
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
public class ReaderController {
    @Autowired
    ReaderService readerService;
    @Autowired
    IssueService issueService;
    @GetMapping("/all")
    public String getReaderAll(Model model){
        Iterable<Reader> list = readerService.getAllReader();
        model.addAttribute("list", list);

        return "readers";
    }
    @GetMapping("/{id}")
    public String getReaderInfo(Model model, @PathVariable Long id) {
        Optional<Reader> list = readerService.getReaderById(id);
        model.addAttribute("list", list.get());
        if (list != null) {
            return "readers";
        } else {
            throw new IllegalArgumentException("Читатель не найден");

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
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
    public ResponseEntity<Reader> createBook(@RequestBody Reader reader) {
        Reader newReader = readerService.addReader(reader);
        return newReader != null
                ? new ResponseEntity<>(newReader, HttpStatus.CREATED)
                : ResponseEntity.badRequest().build();

    }
}
