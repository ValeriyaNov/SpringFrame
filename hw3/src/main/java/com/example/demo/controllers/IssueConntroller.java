package com.example.demo.controllers;
import com.example.demo.model.Issue;
import com.example.demo.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/issue")
public class IssueConntroller {
    @Autowired
    private IssueService service;
    @GetMapping("/all")
    public String getIssueAll(Model model){
        List<Issue> list = service.getAllIssue();
        //String list = "Задачи:";
        model.addAttribute("list", list);
        return "issues";
    }
    @PostMapping
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
    public ResponseEntity<Issue> getInfoIssueById(@PathVariable Long id) {
        final Issue issue;
        issue = service.getInfoById(id);
        return issue != null
                ? ResponseEntity.ok(issue)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> returnedIssue(@PathVariable Long id) {
        Issue reurnedIssue = service.returnedIssue(id);
        return reurnedIssue != null
                ? ResponseEntity.ok(reurnedIssue)
                : ResponseEntity.badRequest().build();
    }


}
