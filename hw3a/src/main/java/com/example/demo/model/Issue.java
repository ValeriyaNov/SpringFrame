package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@OneToOne
    //@MapsId
    //@JoinColumn(name = "book_id")
    //private Long bookId;

    @Column(name = "book_id")
    private Long bookId;
   //@OneToOne
   //@MapsId
   //@JoinColumn(name = "reader_id")
    //private Long readerId;
    @Column(name = "reader_id")
    private Long readerId;
    @Column(name="time_stamp", nullable = false)
    private LocalDateTime timestamp;
    @Column(name="returned_time_stamp")
    private LocalDateTime returnedTimestamp;
    public static long sequence = 1L;
    public Issue(long bookId, long readerId) {
        this.id = sequence++;
        this.bookId = bookId;
        this.readerId = readerId;
        this.timestamp = LocalDateTime.now();
    }
    public Issue() {

    }

    public Long getBookId() {
        return bookId;
    }

    public Long getReaderId() {
        return readerId;
    }


    public LocalDateTime getReturnedTimestamp() {
        return returnedTimestamp;
    }


    public void setReturnedTimestamp(LocalDateTime returnedTimestamp) {

        this.returnedTimestamp = returnedTimestamp;
    }


}
