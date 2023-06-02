package com.devarch.booking.handlers;

import com.devarch.booking.service.BookingUpdateService;
import com.devarch.dto.OrchestratorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class ConsumerHandler {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerHandler.class);

    public ConsumerHandler(BookingUpdateService bookingUpdateService) {
        this.bookingUpdateService = bookingUpdateService;
    }

    BookingUpdateService bookingUpdateService;

    @Bean
    public Consumer<Flux<OrchestratorResponseDTO>> bookingConsumer() {
        return f -> f
                .doOnNext(dto -> logger.debug("Consuming :: {}" , dto))
                .flatMap(responseDTO -> bookingUpdateService.updateOrder(responseDTO))
                .subscribe();
    };

}
