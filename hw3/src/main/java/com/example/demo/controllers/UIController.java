package com.example.demo.controllers;

import com.example.demo.model.Book;
import com.example.demo.model.Issue;
import com.example.demo.model.Reader;
import com.example.demo.service.BookService;
import com.example.demo.service.IssueService;
import com.example.demo.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ui")
public class UIController {
    @Autowired
    BookService bookService;
    @Autowired
    ReaderService readerService;
    @Autowired
    IssueService issuerService;


    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> list = bookService.getAllBook();
        model.addAttribute("list", list);
        return "books";
    }

    @GetMapping("/readers")
    public String getAllReader(Model model) {
        List<Reader> list = readerService.getAllReader();
        model.addAttribute("list", list);
        return "readers";
    }

    @GetMapping("/issues")
    public String getAllIssue(Model model) {
        model.addAttribute("list", issuerService.getAllIssue());
        return "issues";
    }

    @GetMapping("/readers/{id}")
    public String getIssueById(Model model, @PathVariable Long id) {
        Reader reader = readerService.getReaderById(id);
        if (reader == null) {
            throw new NoSuchElementException("читатель не найден");
        }
        List<Issue> issues = issuerService.getIssuesByIdReader(id);
        model.addAttribute("id", reader.getId());
        model.addAttribute("name", reader.getName());
        model.addAttribute("issues", issues);
        return "readerIssue";
    }
}
