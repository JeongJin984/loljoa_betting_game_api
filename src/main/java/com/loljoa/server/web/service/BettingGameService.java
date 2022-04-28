package com.loljoa.server.web.service;

import com.loljoa.server.web.dto.AccountDto;
import com.loljoa.server.web.dto.GameDataDto;

import java.util.List;

public interface BettingGameService {
    List<GameDataDto> getBettingGameData(Long leagueId);
    AccountDto.BettingData bettingToChoice(Long choiceId, Long accountId, Long leagueId, Long gameId, Long point);
    AccountDto getAccountBettingData(String username);

    void cancelBetting(Long accountId, Long choiceId, Long gameId);
}
