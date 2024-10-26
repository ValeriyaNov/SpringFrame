package com.example.demo.service;

import com.example.demo.model.Issue;
import com.example.demo.model.Reader;
import com.example.demo.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private IssueService issuerService;

    public Reader getReaderById(long id) {

        return readerRepository.findById(id);
    }

    public void deleteReaderById(Long id) {

        readerRepository.delete(id);
    }
    public Reader addReader(Reader reader) {

        return readerRepository.save(reader);
    }

    public Reader updateReaderById(Long id, Reader reader) {
        Reader updateReader = getReaderById(id);
        updateReader.setName(reader.getName());
        return readerRepository.save(updateReader);
    }

    public List<Issue> getIssueByIdReader(Long id) {

        return issuerService.getIssuesByIdAllReader(id);
    }
    public List<Reader> getAllReader() {
        return readerRepository.findAll();
    }

}
