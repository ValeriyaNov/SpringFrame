package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title", nullable = false)
    private  String title;


}
