package org.example.controllers;
import org.example.model.Issue;

import org.example.model.IssueRequest;
import org.example.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@Tag(name = "Задачи")
@RequestMapping("/issues")
public class IssueConntroller {
    @Autowired
    private IssueService service;
    @GetMapping("/all")
    @Operation(summary = "Get all issues", description = "Загружает все задачи библиотеки")
    public String getIssueAll(Model model){
        Iterable<Issue> list = service.getAllIssue();
        model.addAttribute("list", list);
        return "issues";
    }
    @PostMapping
    @Operation(summary = "Create new issue", description = "Создание задачи")
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {

        Issue issue;
        try {
            issue = service.issue(request);

        } catch (NoSuchElementException e) {

            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {

            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issue by id", description = "Загружает задачу по id")
    public String getIssueInfo(Model model, @PathVariable Long id) {
        Optional<Issue> list = service.getIssueById(id);
        model.addAttribute("list", list.get());
        if (list != null) {
            return "issues";
        } else {
            throw new IllegalArgumentException("Задача не найдена");

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> returnedIssue(@PathVariable Long id) {
        Issue returnedIssue = service.returnedIssue(id);
        return returnedIssue != null
                ? ResponseEntity.ok(returnedIssue)
                : ResponseEntity.badRequest().build();
    }


}
