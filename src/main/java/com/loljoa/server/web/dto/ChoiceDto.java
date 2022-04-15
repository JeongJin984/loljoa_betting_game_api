package com.loljoa.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChoiceDto {
    private Long choiceId;
    private String name;
    private Long totalPoint;
    private String biggestBetter;
    private Long percent;

    public ChoiceDto(Long choiceId, String name, Long totalPoint, String biggestBetter) {
        this.choiceId = choiceId;
        this.name = name;
        this.totalPoint = totalPoint;
        this.biggestBetter = biggestBetter;
    }
}
