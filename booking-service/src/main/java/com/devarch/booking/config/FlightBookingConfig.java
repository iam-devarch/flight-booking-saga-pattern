package com.devarch.booking.config;

import com.devarch.dto.OrchestratorRequestDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class FlightBookingConfig {

    @Bean
    public Sinks.Many<OrchestratorRequestDTO> orchestratorSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<OrchestratorRequestDTO> flux(Sinks.Many<OrchestratorRequestDTO> orchestratorSink){
        return orchestratorSink.asFlux();
    }

}
