package com.example.aspira.service;

public interface ParsingService {

    /**
     * Retrieves events synchronously for existing top categories.
     *
     * @return A string representation of the events.
     */
    String getEventsSync();

    /**
     * Retrieves events asynchronously for existing top categories.
     * Each top category is processed in a separate thread.
     *
     * @return A string representation of the events.
     */
    String getEventsAsync();

    /**
     * Retrieves events asynchronously in an optimized way using three threads.
     *
     * @return A string representation of the events.
     */
    String getEventsAsyncOptimized();
}
