package com.example.aspira.client.impl;

import com.example.aspira.dto.category.CategoryDto;
import com.example.aspira.dto.event.MainEventDto;
import com.example.aspira.error.RestParseException;
import com.example.aspira.client.RestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class LeonBetRestClientImpl  implements RestClient {

    private final RestTemplate restTemplate;

    @Value("${leonBet.host}")
    private String host;
    @Value("${leonBet.sportPath}")
    private String sportPath;
    @Value("${leonBet.eventPath}")
    private String eventPath;

    /**
     * Retrieves a list of all categories based on their type.
     *
     * @return A list of top categories.
     * @throws RestParseException If an error occurs while parsing the response from the API.
     */
    @Override
    public List<CategoryDto> getAllCategoriesByType(){
        URI url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host)
                .path(sportPath)
                .queryParam("ctag", "en-US")
                .queryParam("flags", "urlv2")
                .build()
                .toUri();

        try {
            ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CategoryDto>>() {
                    });
            return Optional.ofNullable(response.getBody()).orElse(List.of()) ;
        } catch (HttpStatusCodeException exception) {
            log.error("Failed to get categories. Status code : {} ,{}", exception.getStatusCode(), exception.getMessage());
            throw new RestParseException("Failed to get categories", exception.getStatusCode());
        }
    }

    /**
     * Retrieves the main event details for a given league ID.
     *
     * @param leagueId The unique identifier of the league.
     * @return The main event details for the specified league.
     * @throws RestParseException If an error occurs while parsing the response from the API.
     */
    @Override
    public MainEventDto getLeagueEventsById(String leagueId){
        URI url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host)
                .path(eventPath)
                .queryParam("ctag", "en-US")
                .queryParam("league_id", leagueId)
                .queryParam("hideClosed", true)
                .queryParam("flags", "reg,urlv2,mm2,rrc,nodup")
                .build()
                .toUri();

        try {
            ResponseEntity<MainEventDto> response = restTemplate.exchange(url, HttpMethod.GET, null, MainEventDto.class);
            return response.getBody();
        } catch (HttpStatusCodeException exception) {
            log.error("Failed to get categories. Status code : {} ,{}", exception.getStatusCode(), exception.getMessage());
            throw new RestParseException("Failed to get categories", exception.getStatusCode());
        }
    }
}
