package com.example.demo.model;

import java.time.LocalDateTime;

public class Issue {


    public static long sequence = 1L;
    private Long id;
    private Long bookId;
    private Long readerId;

    private LocalDateTime timestamp;

    private LocalDateTime returnedTimestamp;

    public Issue(long bookId, long readerId) {
        this.id = sequence++;
        this.bookId = bookId;
        this.readerId = readerId;
        this.timestamp = LocalDateTime.now();
    }public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LocalDateTime getReturnedTimestamp() {
        return returnedTimestamp;
    }


    public void setReturnedTimestamp(LocalDateTime returnedTimestamp) {

        this.returnedTimestamp = returnedTimestamp;
    }

    public String getDescription() {
        if (returnedTimestamp != null) {
            return "Book with id " + bookId + " was issued to reader with id " + readerId + " at " + timestamp + " and returned at " + returnedTimestamp;
        } else {
            return "Book with id " + bookId + " was issued to reader with id " + readerId + " at " + timestamp + " and has not been returned yet";
        }
    }
}
