package com.example.aspira.service.impl;

import com.example.aspira.client.RestClient;
import com.example.aspira.dto.event.EventDataDto;
import com.example.aspira.service.ParsingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.example.aspira.service.impl.LeonBetMapper.mapEventDataToString;
import static com.example.aspira.service.impl.LeonBetMapper.mapToTopCategories;
import static com.example.aspira.service.impl.LeonBetMapper.mapToTopLeaguesByCategory;
import static com.example.aspira.service.impl.LeonBetUtils.splitListIntoBatches;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeonBetParsingServiceImpl implements ParsingService {

    @Value("${threads.numberOfThreads}")
    private int numberOfThreads;

    private final RestClient leonBetRestClient;

    /**
     * Retrieves events synchronously for existing top categories.
     *
     * @return A string representation of the events.
     */
    @Override
    public String getEventsSync() {
        var categories = leonBetRestClient.getAllCategoriesByType();
        return mapToTopLeaguesByCategory(categories).entrySet().stream()
                .map(leagueIdsByCategory -> {
                    var eventDataList = leagueIdsByCategory.getValue().stream()
                            .map(leagueId -> leonBetRestClient.getLeagueEventsById(leagueId).getEvents().get(0)).toList();
                    return mapEventDataToString(eventDataList, leagueIdsByCategory.getKey());
                })
                .collect(Collectors.joining("\n"));
    }

    /**
     * Retrieves events asynchronously for existing top categories.
     * Each top category is processed in a separate thread.
     *
     * @return A string representation of the events.
     */
    @Override
    public String getEventsAsync() {
        var categories = leonBetRestClient.getAllCategoriesByType();
        List<CompletableFuture<String>> completableFutures = mapToTopLeaguesByCategory(categories).entrySet().stream()
                .map(leagueIdsByCategory -> CompletableFuture.supplyAsync(() -> {
                    List<EventDataDto> eventDataList = leagueIdsByCategory.getValue().stream()
                            .map(leagueId -> leonBetRestClient.getLeagueEventsById(leagueId).getEvents().get(0))
                            .toList();
                    return mapEventDataToString(eventDataList, leagueIdsByCategory.getKey());
                }))
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));

        try {
            allOf.get();
        } catch (InterruptedException | ExecutionException e) {
            log.info(e.getMessage());
            Thread.currentThread().interrupt();
        }

        return completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Retrieves events asynchronously in an optimized way using three threads.
     *
     * @return A string representation of the events.
     */
    @Override
    public String getEventsAsyncOptimized() {
        var categories = leonBetRestClient.getAllCategoriesByType();
        Map<String, Set<String>> topCategories = mapToTopLeaguesByCategory(categories);

        List<String> allValues = topCategories.values().stream()
                .flatMap(Set::stream)
                .toList();

        List<List<String>> batches = splitListIntoBatches(allValues, numberOfThreads);

        List<CompletableFuture<List<EventDataDto>>> completableFutures = batches.stream()
                .map(batch ->
                        CompletableFuture.supplyAsync(() ->
                                batch.stream()
                                        .map(leagueId -> leonBetRestClient.getLeagueEventsById(leagueId).getEvents().get(0))
                                        .toList()))
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));

        try {
            allOf.get();
        } catch (InterruptedException | ExecutionException e) {
            log.info(e.getMessage());
            Thread.currentThread().interrupt();
        }

        List<EventDataDto> eventDataResult = completableFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .toList();

        return mapToTopCategories(topCategories, eventDataResult).entrySet().stream()
                .map(entry -> mapEventDataToString(entry.getValue(), entry.getKey()))
                .collect(Collectors.joining("\n"));
    }
}
