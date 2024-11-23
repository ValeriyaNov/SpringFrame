package org.example.provider;

import org.example.model.IssueRequest;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Optional;
@Service
public class RequestProvider {
    private final WebClient webClient;

    public RequestProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }
    public Optional<IssueRequest> findById(Long id) {
        IssueRequest issueRequest = webClient.get()
                .uri("http://issue-client/issues/{id}", id)
                .retrieve()
                .bodyToMono(IssueRequest.class)
                .block();

        return Optional.ofNullable(issueRequest);
    }
}
