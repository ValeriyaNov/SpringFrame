package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class LoggableAspectConfig {
    @Bean
    LoggableAspect loggableAspect(){
        return new LoggableAspect();
    }
}
