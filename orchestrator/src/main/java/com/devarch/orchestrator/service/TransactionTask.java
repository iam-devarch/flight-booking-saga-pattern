package com.devarch.orchestrator.service;

import reactor.core.publisher.Mono;

public interface TransactionTask {

    TransactionTaskStatus getStatus();
    Mono<Boolean> commit();
    Mono<Boolean> rollback();


}
