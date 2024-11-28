package org.example.service;

import org.example.model.Ebook;
import org.example.repository.EbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EbookService {
    @Autowired
    private EbookRepository ebookRepository;
    public Ebook addBook(Ebook book) {

        return ebookRepository.save(book);
    }


    public Iterable<Ebook> getAllBook() {

        return ebookRepository.findAll();
    }
}
