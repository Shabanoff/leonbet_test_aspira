package com.example.aspira.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeagueDto {
    private String id;
    private String name;
    private boolean top;
}
