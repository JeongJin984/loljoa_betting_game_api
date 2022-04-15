package com.loljoa.server.web.dto;

import lombok.Data;

@Data
public class ChoiceDataDto {
    private Long choiceId;
    private String name;
    private Long totalPoint;
    private String biggestBetter;
    private Long percent;

    public ChoiceDataDto(Long choiceId, String name, Long totalPoint, String biggestBetter) {
        this.choiceId = choiceId;
        this.name = name;
        this.totalPoint = totalPoint;
        this.biggestBetter = biggestBetter;
    }
}
