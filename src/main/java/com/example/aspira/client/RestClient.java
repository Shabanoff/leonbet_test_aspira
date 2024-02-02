package com.example.aspira.client;

import com.example.aspira.dto.category.CategoryDto;
import com.example.aspira.dto.event.MainEventDto;
import com.example.aspira.error.RestParseException;

import java.util.List;

public interface RestClient {

    /**
     * Retrieves a list of all categories based on their type.
     *
     * @return A list of top categories.
     * @throws RestParseException If an error occurs while parsing the response from the API.
     */
    List<CategoryDto> getAllCategoriesByType();

    /**
     * Retrieves the main event details for a given league ID.
     *
     * @param leagueId The unique identifier of the league.
     * @return The main event details for the specified league.
     * @throws RestParseException If an error occurs while parsing the response from the API.
     */
    MainEventDto getLeagueEventsById(String leagueId);
}
