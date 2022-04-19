package com.loljoa.server.db.repository.bettingGame;

import com.loljoa.server.db.entity.BettingGame;

import java.util.List;

public interface BettingGameRepositoryCustom {
    List<BettingGame> getGameData(Long leagueId);

    BettingGame getGameDataById(Long bettingGameId);
}
