package com.devarch.orchestrator.service;

import java.util.List;

public record BookingTransaction(List<TransactionTask> tasks) {

}
