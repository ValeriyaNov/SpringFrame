package com.example.demo.repository;

import com.example.demo.model.Reader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class ReaderRepository {
    private List<Reader> list = new ArrayList<>();

    public ReaderRepository() {
        list.add(new Reader("Анастасия Кочеткова"));
        list.add(new Reader("Петр Петов"));
        list.add(new Reader("Екатерина Старшова"));
        list.add(new Reader("Александр Митин"));
        list.add(new Reader("Мария Лешева"));
        list.add(new Reader("Екатерина Стих"));

    }
    public List<Reader> findAll(){
        return List.copyOf(list);
    }
    public Reader findById(long id){
        return list.stream().filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public String getDescription(long id) {
        StringBuilder description = new StringBuilder();
        list.forEach(reader -> description.append(reader.getName()).append(", "));
        return description.toString();
    }

    public void delete(long id) {

        list.removeIf(book -> book.getId() == id);
    }


    public Reader save(Reader reader) {
        list.add(reader);
        return reader;
    }
}
