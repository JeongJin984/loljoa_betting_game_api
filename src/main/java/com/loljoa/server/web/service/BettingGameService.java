package com.loljoa.server.web.service;

import com.loljoa.server.web.dto.AccountDto;
import com.loljoa.server.web.dto.GameDataDto;

import java.util.List;

public interface BettingGameService {
    List<GameDataDto> getBettingGameData(Long leagueId);
    void bettingToChoice(Long choiceId, Long accountId, Long leagueId, Long point);
    AccountDto getAccountBettingData(String username);
}
