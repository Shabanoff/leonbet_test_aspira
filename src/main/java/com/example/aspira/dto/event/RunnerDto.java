package com.example.aspira.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RunnerDto {
    private String id;
    private String name;
    private String priceStr;
}
