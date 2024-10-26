package com.example.demo.model;

public class Reader {
    public static long sequence = 1L;

    private Long id;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Reader(String name) {
        this(sequence++, name);
    }

    public Reader(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
