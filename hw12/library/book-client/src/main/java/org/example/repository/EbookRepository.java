package org.example.repository;

import org.example.model.Ebook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EbookRepository extends CrudRepository<Ebook, Long> {
}
