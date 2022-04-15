package com.loljoa.server.web.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameDataDto {
    private Long gameId;
    private List<ChoiceDataDto> choices = new ArrayList<>();

    public GameDataDto(Long gameId) {
        this.gameId = gameId;
    }
}
