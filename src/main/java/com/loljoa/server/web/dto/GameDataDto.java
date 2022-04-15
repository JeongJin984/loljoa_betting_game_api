package com.loljoa.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameDataDto {
    private Long gameId;
    private List<ChoiceDto> choices = new ArrayList<>();

    public GameDataDto(Long gameId) {
        this.gameId = gameId;
    }
}
