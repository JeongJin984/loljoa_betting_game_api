package com.loljoa.server.web.service.Impl;

import com.loljoa.server.db.entity.*;
import com.loljoa.server.db.repository.account.AccountRepository;
import com.loljoa.server.db.repository.bettingChoice.BettingChoiceRepository;
import com.loljoa.server.db.repository.bettingGame.BettingGameRepository;
import com.loljoa.server.db.repository.bettingState.BettingStateRepository;
import com.loljoa.server.web.dto.AccountDto;
import com.loljoa.server.web.dto.ChoiceDataDto;
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
    private final BettingChoiceRepository bettingChoiceRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<GameDataDto> getBettingGameData(Long leagueId) {
        List<BettingGame> gameData = bettingGameRepository.getGameData(leagueId);
        List<GameDataDto> result = new ArrayList<>();

        for(BettingGame bg : gameData) {
            GameDataDto gameDataDto = new GameDataDto(bg.getGameId());

            List<BettingChoice> choices = bg.getChoices();
            List<ChoiceDataDto> choiceDtoList = new ArrayList<>();
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
                        biggestBetter = bs.getBetter().getUsername();
                    }
                }

                choiceDtoList.add(new ChoiceDataDto(bc.getChoiceId(), bc.getName(), totalPointOfChoice, biggestBetter, maxPoint));
            }
            if(totalPoint == 0L) {
                for(ChoiceDataDto v : choiceDtoList) {
                    v.setPercent(0L);
                }
            } else {
                for(ChoiceDataDto v : choiceDtoList) {
                    double temp =  (v.getTotalPoint().doubleValue() / totalPoint) * 100;
                    v.setPercent(Math.round(temp));
                }
            }
            gameDataDto.setChoices(choiceDtoList);
            result.add(gameDataDto);
        }
        return result;
    }
    @Override
    public void bettingToChoice(Long choiceId, Long accountId, Long point) {
        BettingChoice choice = bettingChoiceRepository.findById(choiceId);
        Account better = accountRepository.findById(accountId);
        bettingStateRepository.saveAndFlush(new BettingState(choice, better, point));
    }

    @Override
    public AccountDto getAccountBettingData(Long accountId) {
        AccountDto accountDto = new AccountDto();

        List<BettingState> accountBettingState = bettingStateRepository.getAccountBettingState(accountId);
        for(BettingState v : accountBettingState) {
            BettingChoice bettingChoice = bettingChoiceRepository.getChoiceById(v.getChoice().getChoiceId());
            BettingGame gameDataById = bettingGameRepository.getGameDataById(bettingChoice.getTargetGame().getGameId());
            League league = gameDataById.getLeague();
            accountDto.getBettingData().add(
                    new AccountDto.BettingData(
                            league.getLeagueName().split("vs")[0],
                            league.getLeagueName().split("vs")[1],
                            league.getWeekNum(),
                            league.getStartTime(),
                            bettingChoice.getName(),
                            v.getPoint()
                    )
            );
        }
        return accountDto;
    }
}
