package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.Issue;
import com.example.demo.model.Reader;
import com.example.demo.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private IssueService issuerService;

    public Optional<Reader> getReaderById(long id) {

        return readerRepository.findById(id);
    }

    public void deleteReaderById(Long id) {

        readerRepository.deleteById(id);
    }
    public Reader addReader(Reader reader) {

        return readerRepository.save(reader);
    }

    public Reader updateReader(Long id, Reader readerDetails) {
        Optional<Reader> updateReader = readerRepository.findById(id);
        if (updateReader.isPresent()){
            Reader reader = updateReader.get();
            reader.setName(readerDetails.getName());
            return readerRepository.save(reader);
        }
        else{
            throw new IllegalArgumentException("Читатель не найдена");
        }
    }

    public List<Issue> getIssueByIdReader(Long id) {

        return issuerService.getIssuesByIdAllReader(id);
    }
    public Iterable<Reader> getAllReader() {

        return readerRepository.findAll();
    }

}
