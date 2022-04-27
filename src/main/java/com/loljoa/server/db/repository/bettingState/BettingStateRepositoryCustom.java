package com.loljoa.server.db.repository.bettingState;

import com.loljoa.server.db.entity.BettingState;

import java.util.List;

public interface BettingStateRepositoryCustom {

    List<BettingState> getAllBettingState(Long bettingChoiceId);

    List<BettingState> getAccountBettingState(String username);

    List<BettingState> getGameBettingState(Long choiceId);

    List<BettingState> getGameBettingState(long accountId, long choiceId);
}
