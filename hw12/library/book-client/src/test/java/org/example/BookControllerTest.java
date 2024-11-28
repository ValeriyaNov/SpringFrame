package org.example;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Book;
import org.example.repository.BookRepository;
import org.example.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.util.Streamable;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;

public class BookControllerTest extends JUnitSpringBootBase{
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class JUnitBook {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title;
        private String author;

        public JUnitBook(String title, String author) {
            this.title = title;
            this.author = author;
        }
    }

    @Test
    @DisplayName("GET /books/id")
    void testGetByIdSuccess() {
        Book expected = bookService.addBook(new Book("Good Book", "Good Author"));

        JUnitBook responseBody = webTestClient.get()
                .uri("/books/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBook.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getTitle(), responseBody.getTitle());
        Assertions.assertEquals(expected.getAuthor(), responseBody.getAuthor());
    }


    @Test
    @DisplayName("GET /books/all")
    void testGetAll() {
        bookRepository.saveAll(List.of(
                new Book("Book1", "Aothor1"),
                new Book("Book2", "Author2")
        ));
        Iterable<Book> expected = bookService.getAllBook();
        List<Book> list = Streamable.of(expected).toList();

        List<JUnitBook> responseBody = webTestClient.get()
                .uri("/books/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitBook>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(list.size(), responseBody.size());
        for (JUnitBook reader : responseBody) {
            boolean found = list.stream()
                    .filter(it -> Objects.equals(it.getId(), reader.getId()))
                    .anyMatch(it -> Objects.equals(it.getTitle(), reader.getTitle()) && Objects.equals(it.getAuthor(), reader.getAuthor()) );
            Assertions.assertTrue(found);
        }
    }

    @Test
    @DisplayName("POST /books/book/")
    void testCreateReader() {
        JUnitBook createdBook = new JUnitBook("Book3", "Author3");

        JUnitBook responseBody = webTestClient.post()
                .uri("/books/book/")
                .bodyValue(createdBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitBook.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(createdBook.getTitle(), responseBody.getTitle());
        Assertions.assertEquals(createdBook.getAuthor(), responseBody.getAuthor());
        Assertions.assertTrue(bookRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    @DisplayName("PUT /books/id")
    void testUpdateById() {
        Book updatedBook = bookService.addBook(new Book("Book4", "Author4"));
        JUnitBook requestForUpdate = new JUnitBook("BookUpdate", "AuthorUpdate");

        JUnitBook responseBody = webTestClient.put()
                .uri("/books/" + updatedBook.getId())
                .bodyValue(requestForUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBook.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(updatedBook.getId(), responseBody.getId());
        Assertions.assertEquals(requestForUpdate.getTitle(), responseBody.getTitle());
        Assertions.assertEquals(requestForUpdate.getAuthor(), responseBody.getAuthor());
    }

    @Test
    @DisplayName("DELETE /books/id")
    void testDeleteById() {
        Book deletedBook = bookService.addBook(new Book("Book11","Author11"));

        webTestClient.delete()
                .uri("/books/" + deletedBook.getId())
                .exchange()
                .expectStatus().isNoContent();


        Assertions.assertFalse(bookRepository.findById(deletedBook.getId()).isPresent());
    }
}
