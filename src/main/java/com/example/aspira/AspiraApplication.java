package com.example.aspira;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.aspira.service.ParsingService;

@SpringBootApplication
@Slf4j
public class AspiraApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(AspiraApplication.class, args);

        ParsingService leonBetParsingService = applicationContext.getBean(ParsingService.class);
        System.out.println(leonBetParsingService.getEventsAsyncOptimized());

        log.info("Start of statistics calculation");
        long syncStatistic = 0;
        long asyncStatistic = 0;
        long asyncOptimizedStatistic = 0;
        for (int i = 0; i < 8; i++) {
            long startTimeSync = System.currentTimeMillis();
            leonBetParsingService.getEventsSync();
            long endTimeSync = System.currentTimeMillis();
            syncStatistic += (endTimeSync - startTimeSync);

            long startTimeAsync = System.currentTimeMillis();
            leonBetParsingService.getEventsAsync();
            long endTimeAsync = System.currentTimeMillis();
            asyncStatistic += (endTimeAsync - startTimeAsync);

            long startTimeAsyncOptimized = System.currentTimeMillis();
            leonBetParsingService.getEventsAsyncOptimized();
            long endTimeAsyncOptimized = System.currentTimeMillis();
            asyncOptimizedStatistic += (endTimeAsyncOptimized - startTimeAsyncOptimized);
            log.info("Intermediate results: synchronous mode - {} ms, asynchronous mode - {} ms, in asynchronous mode with optimization - {} ms",
                    (endTimeSync - startTimeSync), (endTimeAsync - startTimeAsync), (endTimeAsyncOptimized - startTimeAsyncOptimized));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        log.info("End of statistics calculation. The average time for data retrieval and processing in synchronous mode took - {} ms, " +
                        "in asynchronous mode for each category - {} ms, " +
                        "in asynchronous mode with splitting into three threads with an equal number of leagues - {} ms.",
                syncStatistic/8, asyncStatistic/8, asyncOptimizedStatistic/8);
    }
}
