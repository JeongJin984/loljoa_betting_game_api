package com.loljoa.server.db.repository.bettingState;

import com.loljoa.server.db.entity.BettingState;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.loljoa.server.db.entity.QAccount.account;
import static com.loljoa.server.db.entity.QBettingChoice.bettingChoice;
import static com.loljoa.server.db.entity.QBettingState.bettingState;
import static com.loljoa.server.db.entity.QLeague.league;

@Repository
@RequiredArgsConstructor
public class BettingStateRepositoryImpl implements BettingStateRepositoryCustom {
    private final JPAQueryFactory factory;

    @Override
    public List<BettingState> getAllBettingState(Long bettingChoiceId) {
        List<BettingState> fetch = factory
                .selectFrom(bettingState)
                .join(bettingState.choice(), bettingChoice).fetchJoin()
                .where(bettingState.choice().choiceId.eq(bettingChoiceId))
                .fetch();
        return fetch;
    }

    @Override
    public List<BettingState> getAccountBettingState(String username) {
        return factory
                .selectFrom(bettingState)
                .join(bettingState.choice(), bettingChoice).fetchJoin()
                .join(bettingState.better(), account).fetchJoin()
                .join(bettingState.league(), league).fetchJoin()
                .where(bettingState.better().username.eq(username))
                .fetch();
    }
}
