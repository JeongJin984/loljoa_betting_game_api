package com.loljoa.server.web.service;

import com.loljoa.server.web.dto.GameDataDto;

import java.util.List;

public interface BettingGameService {
    public List<GameDataDto> getBettingGameData(Long leagueId);
}
