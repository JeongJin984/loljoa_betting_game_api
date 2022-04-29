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
import org.springframework.data.jpa.repository.Lock;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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

            for(ChoiceDataDto v : choiceDtoList) {
                if(v.getTotalPoint() == 0) {
                    v.setOdd(0.00);
                } else {
                    v.setOdd((totalPoint / v.getTotalPoint().doubleValue()));
                }
            }
            gameDataDto.setChoices(choiceDtoList);
            result.add(gameDataDto);
        }
        return result;
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Retryable(value = SQLException.class, backoff = @Backoff(delay = 500))
    public AccountDto.BettingData bettingToChoice(Long choiceId, Long accountId, Long leagueId, Long gameId, Long point) {
        BettingChoice choice = bettingChoiceRepository.getChoiceById(choiceId);
        Account better = accountRepository.getAccountById(accountId);
        League league = leagueRepository.getLeagueById(leagueId);
        BettingGame bettingGame = bettingGameRepository.getGameDataById(gameId);

        choice.addTotalPoint(better.getUsername(), point);
        better.usePoint(point);
        bettingGame.addTotalPoint(point);
        bettingStateRepository.save(new BettingState(choice, better, league,point));

        return new AccountDto.BettingData(
                gameId,
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
                                game.getGameId(),
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

    @Override
    @Transactional
    public void cancelBetting(Long accountId, Long choiceId, Long gameId) {
        List<BettingState> gameBettingState = bettingStateRepository.getGameBettingState(accountId, choiceId);
        for(BettingState state : gameBettingState) {
            bettingStateRepository.delete(state);
            state.getBetter().addPoint(state.getPoint());
            BettingChoice choice = bettingChoiceRepository.getChoiceById(choiceId);
            choice.bettingCanceled(state.getPoint());
            BettingGame gameData = bettingGameRepository.getGameDataById(gameId);
            gameData.bettingCanceled(state.getPoint());
        }
    }
}
