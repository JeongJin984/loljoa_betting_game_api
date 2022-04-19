package com.loljoa.server.db.repository.bettingGame;

import com.loljoa.server.db.entity.BettingGame;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.loljoa.server.db.entity.QBettingChoice.bettingChoice;
import static com.loljoa.server.db.entity.QBettingGame.bettingGame;
import static com.loljoa.server.db.entity.QLeague.league;

@Repository
@RequiredArgsConstructor
public class BettingGameRepositoryImpl implements BettingGameRepositoryCustom {
    private final JPAQueryFactory factory;

    @Override
    public List<BettingGame> getGameData(Long leagueId) {
        List<BettingGame> fetch = factory
                .selectFrom(bettingGame)
                .join(bettingGame.league(), league).fetchJoin()
                .join(bettingGame.choices, bettingChoice).fetchJoin()
                .distinct()
                .where(bettingGame.league().leagueId.eq(leagueId))
                .fetch();

        return fetch;
    }

    @Override
    public BettingGame getGameDataById(Long bettingGameId) {
        return factory
                .selectFrom(bettingGame)
                .join(bettingGame.league(), league).fetchJoin()
                .distinct()
                .where(bettingGame.gameId.eq(bettingGameId))
                .fetchOne();
    }
}
