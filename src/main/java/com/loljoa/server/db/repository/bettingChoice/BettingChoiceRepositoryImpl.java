package com.loljoa.server.db.repository.bettingChoice;

import com.loljoa.server.db.entity.BettingChoice;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.loljoa.server.db.entity.QBettingChoice.bettingChoice;
import static com.loljoa.server.db.entity.QBettingGame.bettingGame;

@Repository
@RequiredArgsConstructor
public class BettingChoiceRepositoryImpl implements BettingChoiceRepositoryCustom {
    private final JPAQueryFactory factory;

    @Override
    public BettingChoice getChoiceById(Long choiceId) {
        return factory
                .selectFrom(bettingChoice)
                .join(bettingChoice.targetGame(), bettingGame).fetchJoin()
                .where(bettingChoice.choiceId.eq(choiceId))
                .fetchOne();
    }
}
