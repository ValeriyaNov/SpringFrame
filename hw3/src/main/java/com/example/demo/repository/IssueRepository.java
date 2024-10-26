package com.example.demo.repository;

import com.example.demo.model.Issue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class IssueRepository {
    private List<Issue> list = new ArrayList<>();

    public List<Issue> findAll(){
        return List.copyOf(list);
    }
    public Issue findById(long id) {
        return list.stream().filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public List<Issue> getIssuesByIdReader(Long id) {

        return list.stream()
           .filter(issue -> issue.getReaderId().equals(id))
            .filter(issue -> issue.getReturnedTimestamp() == null)
            .toList();
  }
    public List<Issue> getIssuesByAllReader(Long id) {

        return list.stream()
                .filter(issue -> issue.getReaderId().equals(id))
                .toList();
    }


    public void save(Issue issue) {
        list.add(issue);
    }


}
