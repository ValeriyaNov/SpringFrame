package org.example.service;


import org.example.model.Issue;
import org.example.model.IssueRequest;
import org.example.provider.BookProvider;
import org.example.provider.ReaderProvider;
import org.example.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IssueService {

    private final BookProvider bookProvider;
    private final ReaderProvider readerProvider;
    private final IssueRepository issueRepository;

    @Value("${application.max-allowed-books:1}")
    private int maxAllowedBooks;

    public IssueService( BookProvider bookProvider, ReaderProvider readerProvider, IssueRepository issueRepository) {
        this.bookProvider = bookProvider;
        this.readerProvider = readerProvider;
        this.issueRepository = issueRepository;
    }


    public Issue issue(IssueRequest request)throws NoSuchElementException, RuntimeException {

        if (bookProvider.findById(request.getBookId())==null) {

            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
        }
        if (readerProvider.findById(request.getReaderId())==null) {

            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
        }

        if (issueRepository.findByReaderIdAndReturnedTimestamp(request.getReaderId(), null).size() >= maxAllowedBooks) {

            throw new RuntimeException("Отказано в выдаче читателю ID - " + request.getReaderId() + " из-за превышения лимита");
        }

        Issue issue = new Issue(request.getBookId(), request.getReaderId());

        issueRepository.save(issue);

        return issue;
    }

    public Optional<Issue> getIssueById(long id) {

        return issueRepository.findById(id);
    }

    public List<Issue> getIssuesByIdReader(Long id) {

        return issueRepository.findByReaderIdAndReturnedTimestamp(id, null);
    }
    public List<Issue> getIssuesByIdAllReader(Long id) {

        return issueRepository.findByReaderId(id);
    }

    public Issue returnedIssue(Long id) {
        Optional<Issue> returnedIssue = issueRepository.findById(id);
        returnedIssue.get().setReturnedTimestamp(LocalDateTime.now());
        return issueRepository.save(returnedIssue.get());
    }
    public Iterable<Issue> getAllIssue() {
        return issueRepository.findAll();
    }

}
