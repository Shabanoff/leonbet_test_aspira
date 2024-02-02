package com.example.aspira.dto.event;

import com.example.aspira.dto.category.LeagueDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDataDto {

    private String id;
    private String name;
    private long kickoff;
    private List<CompetitorDto> competitors;
    private LeagueDto league;
    private List<MarketDto> markets;

}
