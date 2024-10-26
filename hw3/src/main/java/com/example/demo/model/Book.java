package com.example.demo.model;
import lombok.Data;

@Data
public class Book {
    private static long genId = 0;
    private final Long id;
    private final String name;

    public Book(String name) {
        id = genId++;
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
