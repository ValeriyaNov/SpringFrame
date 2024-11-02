package com.example.demo.repository;

import com.example.demo.model.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface IssueRepository extends CrudRepository<Issue, Long> {
    List<Issue> findByReaderIdAndReturnedTimestamp(Long readerId, LocalDateTime returnedTime);

    List<Issue> findByReaderId(Long readerId);


}
