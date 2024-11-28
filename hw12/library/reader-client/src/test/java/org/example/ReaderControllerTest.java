package org.example;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Reader;
import org.example.repository.ReaderRepository;
import org.example.service.ReaderService;
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

public class ReaderControllerTest extends JUnitSpringBootBase{
    @MockBean
    WebTestClient webTestClient;
    @Autowired
    ReaderService readerService;
    @Autowired
    ReaderRepository readerRepository;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class JUnitReader {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;

        public JUnitReader(String name) {
            this.name = name;
        }
    }

    @Test
    @DisplayName("GET /readers/id")
    void testGetByIdSuccess() {
        Reader expected = readerService.addReader(new Reader("Tretyakov Ivan"));

        JUnitReader responseBody = webTestClient.get()
                .uri("/readers/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitReader.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }
    

    @Test
    @DisplayName("GET /readers/all")
    void testGetAll() {
        readerRepository.saveAll(List.of(
                new Reader("Oleg"),
                new Reader("Anna")
        ));
        Iterable<Reader> expected = readerService.getAllReader();
        List<Reader> list = Streamable.of(expected).toList();

        List<JUnitReader> responseBody = webTestClient.get()
                .uri("/readers/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitReader>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(list.size(), responseBody.size());
        for (JUnitReader reader : responseBody) {
            boolean found = list.stream()
                    .filter(it -> Objects.equals(it.getId(), reader.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), reader.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    @DisplayName("POST /readers/reader/")
    void testCreateReader() {
        JUnitReader createdReader = new JUnitReader("Alex");

        JUnitReader responseBody = webTestClient.post()
                .uri("/readers/reader/")
                .bodyValue(createdReader)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitReader.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(createdReader.getName(), responseBody.getName());
        Assertions.assertTrue(readerRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    @DisplayName("PUT /readers/id")
    void testUpdateById() {
        Reader updatedReader = readerService.addReader(new Reader("John"));
        JUnitReader requestForUpdate = new JUnitReader("David");

        JUnitReader responseBody = webTestClient.put()
                .uri("/readers/" + updatedReader.getId())
                .bodyValue(requestForUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitReader.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(updatedReader.getId(), responseBody.getId());
        Assertions.assertEquals(requestForUpdate.getName(), responseBody.getName());
    }

    @Test
    @DisplayName("DELETE /readers/id")
    void testDeleteById() {
        Reader deletedReader = readerService.addReader(new Reader("Mike"));

        webTestClient.delete()
                .uri("/readers/" + deletedReader.getId())
                .exchange()
                .expectStatus().isNoContent();


        Assertions.assertFalse(readerRepository.findById(deletedReader.getId()).isPresent());
    }
}
