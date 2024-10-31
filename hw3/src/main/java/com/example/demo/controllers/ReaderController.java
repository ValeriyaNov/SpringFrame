package com.example.demo.controllers;
import com.example.demo.model.Book;
import com.example.demo.model.Issue;
import com.example.demo.model.Reader;
import com.example.demo.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    ReaderService readerService;
    @GetMapping("/all")
    public String getReaderAll(Model model){
        List<Reader> list = readerService.getAllReader();
        model.addAttribute("list", list);

        return "readers";
    }
    @GetMapping("{id}")
    public String getReaderInfo(@PathVariable Long id) {
        Reader reader = readerService.getReaderById(id);
        if (reader != null) {
            return reader.getName();
        } else {
            return  "Читатель с id=" + id + " не найден";
        }
    }

    @DeleteMapping("{id}")
    public String deleteReader(@PathVariable Long id) {
        Reader reader = readerService.getReaderById(id);
        if (reader != null) {
            readerService.deleteReaderById(id);
            return "Читатель с id=" + id + " удален";
        } else {
            return "Читатель с id=" + id + " не найден";
        }
    }
    @GetMapping("/reader/{id}/issue")
    public List<Issue> getReaderIssues(@PathVariable Long id) {
        Reader reader = readerService.getReaderById(id);
        if (reader != null) {
            return readerService.getIssueByIdReader(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Читатель с id=" + id + " не найден");
        }
    }
    @PostMapping("/reader/{name}")
    public Reader createReader(@PathVariable String name) {
        Reader newReader = new Reader(name);
        readerService.addReader(newReader);
        return newReader;
    }
}
