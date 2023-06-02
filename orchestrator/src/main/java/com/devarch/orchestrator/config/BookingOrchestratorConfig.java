package com.devarch.orchestrator.config;


import com.devarch.dto.OrchestratorRequestDTO;
import com.devarch.dto.OrchestratorResponseDTO;
import com.devarch.orchestrator.service.BookingOrchestratorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class BookingOrchestratorConfig {


    public BookingOrchestratorConfig(BookingOrchestratorService bookingOrchestratorService) {
        this.bookingOrchestratorService = bookingOrchestratorService;
    }

    private final BookingOrchestratorService bookingOrchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequestDTO>, Flux<OrchestratorResponseDTO>> bookingProcessor(){
        return flux -> flux
                            .flatMap(bookingOrchestratorService::bookFlight)
                            .doOnNext(dto -> System.out.printf("Status::%s ... BookingId::%s %n", dto.bookingStatus(), dto.bookingId() ));
    }

}
