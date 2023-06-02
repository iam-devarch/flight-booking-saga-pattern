package com.devarch.booking.handlers;


import com.devarch.dto.OrchestratorRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Configuration
public class SupplierHandler {

    private static final Logger logger = LoggerFactory.getLogger(SupplierHandler.class);
    @Autowired
    private Flux<OrchestratorRequestDTO> flux;

    @Bean
    public Supplier<Flux<OrchestratorRequestDTO>> bookingSupplier(){
        return () -> flux.doOnNext(dto -> logger.debug("Emitting event for :: {}", dto));
    };



}
