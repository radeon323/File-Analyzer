package com.olshevchenko.entity;

import lombok.*;

import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ResultOfTheFileAnalysis {
    private String givenWord;
    private List<String> sentencesWithGivenWord;
    private int wordEntryCount;
}
