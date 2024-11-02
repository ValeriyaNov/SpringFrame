package com.example.demo.controllers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class IssueRequest {


    private long readerId;

    private long bookId;
}
