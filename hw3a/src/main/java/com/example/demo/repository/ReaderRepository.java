package com.example.demo.repository;

import com.example.demo.model.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {

}
