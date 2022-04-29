package com.loljoa.server.web.service.Impl;

import com.loljoa.server.db.entity.BettingChoice;
import com.loljoa.server.db.entity.BettingGame;
import com.loljoa.server.db.entity.BettingState;
import com.loljoa.server.db.repository.bettingGame.BettingGameRepository;
import com.loljoa.server.db.repository.bettingState.BettingStateRepository;
import com.loljoa.server.web.service.PointDistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PointDistributeServiceImpl implements PointDistributionService {
    private final BettingStateRepository bettingStateRepository;
    private final BettingGameRepository bettingGameRepository;

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Retryable(
            value = SQLException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 1000)
    )
    public void distributePoint(Long gameId, Long choiceId) {
        BettingGame gameData = bettingGameRepository.getGameDataById(gameId);
        for(BettingChoice bc : gameData.getChoices()) {
            List<BettingState> gameBettingState = bettingStateRepository.getGameBettingState(bc.getChoiceId());
            if(bc.getChoiceId().equals(choiceId)) {
                for(BettingState bs : gameBettingState) {
                    double odd = (gameData.getTotalPoint().doubleValue() / bc.getTotalPoint());
                    long totalRevenue = (long)(bs.getPoint().doubleValue() * odd);
                    bs.getBetter().addPoint(totalRevenue);
                    bettingStateRepository.delete(bs);
                }
            }
        }
    }
}
