package com.example.aspira.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketDto {

    private String id;
    private String name;
    private List<RunnerDto> runners;
}
