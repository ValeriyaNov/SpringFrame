package com.example.demo.service;

import com.example.demo.controllers.IssueRequest;
import com.example.demo.model.Issue;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.IssueRepository;
import com.example.demo.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
@Service
public class IssueService {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    @Value("${application.max-allowed-books:1}")
    private int maxAllowedBooks;

    public IssueService(BookRepository bookRepository, ReaderRepository readerRepository, IssueRepository issueRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.issueRepository = issueRepository;
    }


    public Issue issue(IssueRequest request)throws NoSuchElementException, RuntimeException {

        if (bookRepository.findById(request.getBookId())==null) {

            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
        }
        if (readerRepository.findById(request.getReaderId())==null) {

            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
        }

        if (issueRepository.getIssuesByIdReader(request.getReaderId()).size() >= maxAllowedBooks) {

            throw new RuntimeException("Отказано в выдаче читателю ID - " + request.getReaderId() + " из-за превышения лимита");
        }

        Issue issue = new Issue(request.getBookId(), request.getReaderId());

        issueRepository.save(issue);

        return issue;
    }

    public Issue getInfoById(Long id) {

        return issueRepository.findById(id);
    }

    public List<Issue> getIssuesByIdReader(Long id) {

        return issueRepository.getIssuesByIdReader(id);
    }
    public List<Issue> getIssuesByIdAllReader(Long id) {

        return issueRepository.getIssuesByAllReader(id);
    }

    public Issue returnedIssue(Long id) {
        Issue returnedIssue = issueRepository.findById(id);
        returnedIssue.setReturnedTimestamp(LocalDateTime.now());
        return returnedIssue;
    }
    public List<Issue> getAllIssue() {
        return issueRepository.findAll();
    }

}
