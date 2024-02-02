package com.example.aspira.service.impl;

import com.example.aspira.dto.category.CategoryDto;
import com.example.aspira.dto.category.CategoryName;
import com.example.aspira.dto.category.LeagueDto;
import com.example.aspira.dto.event.EventDataDto;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class LeonBetMapper {

    public static String mapEventDataToString(List<EventDataDto> eventDataList, String categoryName) {
        StringBuilder result = new StringBuilder();

        eventDataList.forEach(eventData -> {
            result.append(String.format("%s, %s%n",
                    categoryName,
                    eventData.getLeague().getName()));
            result.append(String.format("\t%s, %s, %s%n",
                    eventData.getName(),
                    Instant.ofEpochMilli(eventData.getKickoff()).toString(),
                    eventData.getId()));

            if (!CollectionUtils.isEmpty(eventData.getMarkets())) {
                eventData.getMarkets().forEach(market -> {
                    result.append(String.format("\t\t%s%n", market.getName()));

                    market.getRunners().forEach(runner -> result.append(String.format("\t\t\t%s, %s, %s%n",
                            runner.getName(),
                            runner.getPriceStr(),
                            runner.getId())));
                });
            }
        });

        return result.toString();
    }

    public static Map<String, Set<String>> mapToTopLeaguesByCategory(List<CategoryDto> categories) {
        return categories.stream()
                .filter(category -> CategoryName.isCategoryPresent(category.getName()))
                .collect(Collectors.toMap(
                        CategoryDto::getName,
                        category -> category.getRegions().stream()
                                .flatMap(region -> region.getLeagues().stream())
                                .filter(LeagueDto::isTop)
                                .map(LeagueDto::getId)
                                .collect(Collectors.toSet())
                ));
    }

    public static Map<String, List<EventDataDto>> mapToTopCategories(Map<String, Set<String>> topCategories, List<EventDataDto> eventDataList) {
        return topCategories.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> getEventDataForLeague(entry.getValue(), eventDataList)
                ));
    }

    private static List<EventDataDto> getEventDataForLeague(Set<String> leagueIds, List<EventDataDto> eventDataList) {
        return eventDataList.stream()
                .filter(eventData -> leagueIds.contains(eventData.getLeague().getId()))
                .toList();
    }
}
