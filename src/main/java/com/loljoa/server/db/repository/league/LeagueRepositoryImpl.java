package com.loljoa.server.db.repository.league;

import com.loljoa.server.db.entity.League;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.loljoa.server.db.entity.QLeague.league;

@Repository
@RequiredArgsConstructor
public class LeagueRepositoryImpl implements LeagueRepositoryCustom {
    private final JPAQueryFactory factory;

    @Override
    public League getLeagueById(Long leagueId) {
        return factory
                .selectFrom(league)
                .where(league.leagueId.eq(leagueId))
                .fetchOne();
    }
}