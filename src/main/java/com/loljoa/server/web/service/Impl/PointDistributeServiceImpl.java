package com.loljoa.server.web.service.Impl;

import com.loljoa.server.db.entity.BettingChoice;
import com.loljoa.server.db.entity.BettingGame;
import com.loljoa.server.db.entity.BettingState;
import com.loljoa.server.db.repository.bettingGame.BettingGameRepository;
import com.loljoa.server.db.repository.bettingState.BettingStateRepository;
import com.loljoa.server.web.service.PointDistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointDistributeServiceImpl implements PointDistributionService {
    private final BettingStateRepository bettingStateRepository;
    private final BettingGameRepository bettingGameRepository;

    @Override
    public void distributePoint(Long gameId, Long choiceId) {
        BettingGame gameData = bettingGameRepository.getGameDataById(gameId);
        for(BettingChoice bc : gameData.getChoices()) {
            List<BettingState> gameBettingState = bettingStateRepository.getGameBettingState(bc.getChoiceId());
            if(bc.getChoiceId().equals(choiceId)) {
                for(BettingState bs : gameBettingState) {
                    bs.getBetter().addPoint(bs.getPoint() * (gameData.getTotalPoint() / bc.getTotalPoint()));
                }
            }
        }
        bettingGameRepository.delete(gameData);
    }
}
