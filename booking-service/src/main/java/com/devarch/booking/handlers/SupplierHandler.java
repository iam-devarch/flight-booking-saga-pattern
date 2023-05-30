package com.devarch.booking.handlers;


import com.devarch.dto.OrchestratorRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class SupplierHandler {

    @Autowired
    private Flux<OrchestratorRequestDTO> flux;

    @Bean
    public Supplier<Flux<OrchestratorRequestDTO>> bookingSupplier(){
        return () -> flux;
    };

}
