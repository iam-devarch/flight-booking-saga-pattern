package com.devarch.orchestrator.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public WebClientConfig(OrchestratorProperties orchestratorProperties) {
        this.orchestratorProperties = orchestratorProperties;
    }

    private final OrchestratorProperties orchestratorProperties;

    @Bean
    @Qualifier("paymentClient")
    public WebClient paymentClient(){
        return WebClient.builder()
                .baseUrl(orchestratorProperties.payment())
                .build();
    }

    @Bean
    @Qualifier("seatReservationClient")
    public WebClient seatReservationClient() {
        return WebClient.builder()
                .baseUrl(orchestratorProperties.seatReservation())
                .build();
    }

}
