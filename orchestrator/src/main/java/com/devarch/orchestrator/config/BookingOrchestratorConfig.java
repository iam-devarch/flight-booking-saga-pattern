package com.devarch.orchestrator.config;


import com.devarch.dto.OrchestratorRequestDTO;
import com.devarch.dto.OrchestratorResponseDTO;
import com.devarch.enums.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class BookingOrchestratorConfig {



    @Bean
    public Function<Flux<OrchestratorRequestDTO>, Flux<OrchestratorResponseDTO>> bookingProcessor(){
        return flux -> flux
                            .flatMap(dto -> Mono.just(new OrchestratorResponseDTO(BookingStatus.BOOKING_COMPLETED)))
                            .doOnNext(dto -> System.out.println("Status : " + dto.status()));
    }

}
