package com.example.demo.controllers;

public class IssueRequest {
    private long readerId;

    public long getReaderId() {
        return readerId;
    }

    public void setReaderId(long readerId) {
        this.readerId = readerId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    /**
     * Идентификатор книги
     */
    private long bookId;
}
