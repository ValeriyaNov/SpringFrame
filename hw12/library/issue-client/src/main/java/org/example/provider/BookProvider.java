package org.example.provider;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.print.Book;
import java.util.Optional;

@Service
public class BookProvider {
    private final WebClient webClient;

    public BookProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }
    public Optional<Book> findById(Long id) {
        Book book = webClient.get()
                .uri("http://book-client/books/{id}", id)
                .retrieve()
                .bodyToMono(Book.class)
                .block();

        return Optional.ofNullable(book);
    }
}
