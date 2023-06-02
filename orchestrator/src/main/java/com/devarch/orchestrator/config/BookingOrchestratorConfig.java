package com.devarch.orchestrator.config;


import com.devarch.dto.OrchestratorRequestDTO;
import com.devarch.dto.OrchestratorResponseDTO;
import com.devarch.orchestrator.service.BookingOrchestratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class BookingOrchestratorConfig {

    private static final Logger logger = LoggerFactory.getLogger(BookingOrchestratorConfig.class);
    public BookingOrchestratorConfig(BookingOrchestratorService bookingOrchestratorService) {
        this.bookingOrchestratorService = bookingOrchestratorService;
    }

    private final BookingOrchestratorService bookingOrchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequestDTO>, Flux<OrchestratorResponseDTO>> bookingProcessor(){
        return flux -> flux
                            .flatMap(bookingOrchestratorService::bookFlight)
                            .doOnNext(dto -> logger.debug("Status:: {} ... BookingId::{}", dto.bookingStatus(), dto.bookingId() ));
    }

}
