package org.example.provider;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.Reader;
import java.util.Optional;

@Service
public class ReaderProvider {
    private final WebClient webClient;

    public ReaderProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }

    public Optional<Reader> findById(Long id) {
        Reader reader = webClient.get()
                .uri("http://reader-client/readers/{id}", id)
                .retrieve()
                .bodyToMono(Reader.class)
                .block();

        return Optional.ofNullable(reader);
    }
}
