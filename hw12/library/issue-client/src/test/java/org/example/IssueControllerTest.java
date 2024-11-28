package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Issue;
import org.example.model.IssueRequest;
import org.example.repository.IssueRepository;
import org.example.service.IssueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.util.Streamable;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class IssueControllerTest extends JUnitSpringBootBase{
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueService issueService;

    @Autowired
    IssueRepository issueRepository;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class JUnitIssue {
        private Long id;
        private Long bookId;
        private Long readerId;
        private LocalDateTime timestamp;
        private LocalDateTime returnedTimestamp;

        public JUnitIssue(Long bookId, Long readerId) {
            this.bookId = bookId;
            this.readerId = readerId;
        }
    }

    @Test
    @DisplayName("GET /issues/id ")
    void testGetByIdSuccess() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm-HH-MM-dd-yy");
        Issue expected = issueService.issue(new IssueRequest(5,1));

        JUnitIssue responseBody = webTestClient.get()
                .uri("/issues/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expected.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(dtf.format(expected.getTimestamp()), dtf.format(responseBody.getTimestamp()));
        Assertions.assertNull(responseBody.getReturnedTimestamp());

    }


    @Test
    @DisplayName("GET /issues/all")
    void testGetAll() {
        Issue expected1 = issueService.issue(new IssueRequest(7,2));
        Issue expected2 = issueService.issue(new IssueRequest(9,1));
        issueRepository.saveAll(List.of(expected1, expected2));
        Iterable<Issue> expected = issueService.getAllIssue();
        List<Issue> list = Streamable.of(expected).toList();

        List<JUnitIssue> responseBody = webTestClient.get()
                .uri("/issues/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitIssue>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(list.size(), responseBody.size());
        for (JUnitIssue issue : responseBody) {
            boolean found = list.stream()
                    .filter(it -> Objects.equals(it.getId(), issue.getId()))
                    .filter(it -> Objects.equals(it.getBookId(), issue.getBookId()))
                    .filter(it -> Objects.equals(it.getReaderId(), issue.getReaderId()))
                    .anyMatch(it -> Objects.equals(it.getTimestamp(), issue.getTimestamp()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    @DisplayName("POST /issues")
    void testCreateIssue() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm-HH-MM-dd-yy");
        Issue expected = issueService.issue(new IssueRequest(13,2));
        JUnitIssue createdIssue = new JUnitIssue(expected.getBookId(), expected.getReaderId());

        JUnitIssue responseBody = webTestClient.post()
                .uri("/issues")
                .bodyValue(createdIssue)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(createdIssue.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(createdIssue.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(dtf.format(LocalDateTime.now()), dtf.format(responseBody.getTimestamp()));
        Assertions.assertNull(responseBody.getReturnedTimestamp());
        Assertions.assertTrue(issueRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    @DisplayName("PUT /issues/id")
    void returnedIssue() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm-HH-MM-dd-yy");
        //String text = dtf.format( LocalDateTime.now() );
        Issue expected  = issueService.issue(new IssueRequest(9,2));
        Issue updatedIssue = issueService.returnedIssue(expected.getId());
        JUnitIssue requestForUpdate = new JUnitIssue(updatedIssue.getReaderId(),updatedIssue.getBookId());

        JUnitIssue responseBody = webTestClient.put()
                .uri("/issues/" + expected.getId())
                .bodyValue(requestForUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expected.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(dtf.format(LocalDateTime.now()), dtf.format(responseBody.getReturnedTimestamp()));
        Assertions.assertTrue(issueRepository.findById(expected.getId()).isPresent());
    }


}
