package com.example.aspira.dto.category;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum CategoryName {

    FOOTBALL("Football"),
    ICE_HOCKEY("Ice Hockey"),
    TENNIS("Tennis"),
    BASKETBALL("Basketball");

    private final String name;
    CategoryName(String name) {
        this.name = name;
    }

    private static final Set<String> ALL_CATEGORY_NAMES = Arrays.stream(CategoryName.values())
            .map(CategoryName::getName)
            .collect(Collectors.toSet());

    public static boolean isCategoryPresent(String category) {
        return ALL_CATEGORY_NAMES.contains(category);
    }

}
