package com.loljoa.server.web.dto;

import lombok.Data;

@Data
public class ChoiceDataDto {
    private Long choiceId;
    private String name;
    private Long totalPoint;
    private String biggestBetter;
    private Long biggestPoint;
    private Double odd;

    public ChoiceDataDto(Long choiceId, String name, Long totalPoint, String biggestBetter, Long biggestPoint) {
        this.choiceId = choiceId;
        this.name = name;
        this.totalPoint = totalPoint;
        this.biggestBetter = biggestBetter;
        this.biggestPoint = biggestPoint;
    }
}
