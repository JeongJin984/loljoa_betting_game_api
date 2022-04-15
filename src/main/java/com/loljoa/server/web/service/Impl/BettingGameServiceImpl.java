package com.loljoa.server.web.service.Impl;

import com.loljoa.server.db.entity.BettingChoice;
import com.loljoa.server.db.entity.BettingGame;
import com.loljoa.server.db.entity.BettingState;
import com.loljoa.server.db.repository.bettingGame.BettingGameRepository;
import com.loljoa.server.db.repository.bettingState.BettingStateRepository;
import com.loljoa.server.web.dto.ChoiceDto;
import com.loljoa.server.web.dto.GameDataDto;
import com.loljoa.server.web.service.BettingGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BettingGameServiceImpl implements BettingGameService {
    private final BettingGameRepository bettingGameRepository;
    private final BettingStateRepository bettingStateRepository;

    @Override
    public List<GameDataDto> getBettingGameData(Long leagueId) {
        List<BettingGame> gameData = bettingGameRepository.getGameData(leagueId);
        List<GameDataDto> result = new ArrayList<>();

        for(BettingGame bg : gameData) {
            GameDataDto gameDataDto = new GameDataDto(bg.getGameId());

            List<BettingChoice> choices = bg.getChoices();
            List<ChoiceDto> choiceDtoList = new ArrayList<>();
            Long totalPoint = 0L;
            for (BettingChoice bc : choices) {
                Long totalPointOfChoice = 0L;
                String biggestBetter = "";
                long maxPoint = 0L;
                List<BettingState> bettingState = bettingStateRepository.getAllBettingState(bc.getChoiceId());
                for(BettingState bs : bettingState) {
                    Long curPoint = bs.getPoint();
                    totalPoint += curPoint;
                    totalPointOfChoice += curPoint;
                    if(maxPoint < curPoint) {
                        maxPoint = curPoint;
                        biggestBetter = bs.getBetter().getNickname();
                    }
                }
                choiceDtoList.add(new ChoiceDto(bc.getChoiceId(), bc.getName(), totalPointOfChoice, biggestBetter));
            }
            if(totalPoint == 0L) {
                for(ChoiceDto v : choiceDtoList) {
                    v.setPercent(0L);
                }
            } else {
                for(ChoiceDto v : choiceDtoList) {
                    double temp =  (v.getTotalPoint().doubleValue() / totalPoint);
                    v.setPercent(Math.round(temp));
                }
            }
            gameDataDto.setChoices(choiceDtoList);
            result.add(gameDataDto);
        }
        return result;
    }
}
