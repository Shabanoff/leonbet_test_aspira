package com.example.aspira.service.impl;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class LeonBetUtils {

    public static <T> List<List<T>> splitListIntoBatches(List<T> list, int numberOfBatches) {
        int size = list.size();
        int batchSize = size / numberOfBatches;
        int remainder = size % numberOfBatches;

        List<List<T>> batches = new ArrayList<>();
        int start = 0;

        for (int i = 0; i < numberOfBatches; i++) {
            int end = start + batchSize + (i < remainder ? 1 : 0);
            batches.add(list.subList(start, end));
            start = end;
        }

        return batches;
    }
}
