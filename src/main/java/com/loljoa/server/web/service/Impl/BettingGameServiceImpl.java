package com.loljoa.server.web.service.Impl;

import com.loljoa.server.db.entity.*;
import com.loljoa.server.db.repository.account.AccountRepository;
import com.loljoa.server.db.repository.bettingChoice.BettingChoiceRepository;
import com.loljoa.server.db.repository.bettingGame.BettingGameRepository;
import com.loljoa.server.db.repository.bettingState.BettingStateRepository;
import com.loljoa.server.db.repository.league.LeagueRepository;
import com.loljoa.server.web.dto.AccountDto;
import com.loljoa.server.web.dto.ChoiceDataDto;
import com.loljoa.server.web.dto.GameDataDto;
import com.loljoa.server.web.service.BettingGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BettingGameServiceImpl implements BettingGameService {
    private final BettingGameRepository bettingGameRepository;
    private final BettingStateRepository bettingStateRepository;
    private final BettingChoiceRepository bettingChoiceRepository;
    private final AccountRepository accountRepository;
    private final LeagueRepository leagueRepository;

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
                totalPoint += bc.getTotalPoint();
                choiceDtoList.add(
                        new ChoiceDataDto(
                                bc.getChoiceId(),
                                bc.getName(),
                                bc.getTotalPoint(),
                                bc.getBiggestBetter(),
                                bc.getBiggestPoint()
                        )
                );
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
    @Transactional
    public AccountDto.BettingData bettingToChoice(Long choiceId, Long accountId, Long leagueId, Long gameId, Long point) {
        BettingChoice choice = bettingChoiceRepository.getChoiceById(choiceId);
        Account better = accountRepository.getAccountById(accountId);
        League league = leagueRepository.getLeagueById(leagueId);
        BettingGame bettingGame = bettingGameRepository.getGameDataById(gameId);
        bettingStateRepository.save(new BettingState(choice, better, league,point));
        choice.addTotalPoint(better.getUsername(), point);
        better.usePoint(point);
        bettingGame.addTotalPoint(point);
        return new AccountDto.BettingData(
                league.getLeagueName().split("vs")[0],
                league.getLeagueName().split("vs")[1],
                league.getWeekNum(),
                league.getStartTime(),
                choice.getChoiceId(),
                choice.getName(),
                choice.getTotalPoint(),
                bettingGame.getTotalPoint(),
                point
        );
    }

    @Override
    public AccountDto getAccountBettingData(String username) {
        List<BettingState> accountBettingState = bettingStateRepository.getAccountBettingState(username);

        if(accountBettingState.size() > 0) {
            Account better = accountBettingState.get(0).getBetter();
            AccountDto accountDto = new AccountDto(better.getAccountId(), better.getUsername(), better.getPoint());

            for(BettingState v : accountBettingState) {
                BettingGame game = v.getChoice().getTargetGame();
                accountDto.getBettingData().add(
                        new AccountDto.BettingData(
                                v.getLeague().getLeagueName().split("vs")[0],
                                v.getLeague().getLeagueName().split("vs")[1],
                                v.getLeague().getWeekNum(),
                                v.getLeague().getStartTime(),
                                v.getChoice().getChoiceId(),
                                v.getChoice().getName(),
                                v.getChoice().getTotalPoint(),
                                game.getTotalPoint(),
                                v.getPoint()
                        )
                );
            }
            return accountDto;
        } else {
            Account account = accountRepository.getAccountByUsername(username);
            return new AccountDto(account.getAccountId(), account.getUsername(), account.getPoint());
        }
    }
}
