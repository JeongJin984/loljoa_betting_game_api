package com.loljoa.server.db.repository.bettingState;

import com.loljoa.server.db.entity.BettingState;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.loljoa.server.db.entity.QBettingChoice.bettingChoice;
import static com.loljoa.server.db.entity.QBettingState.bettingState;

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
}
