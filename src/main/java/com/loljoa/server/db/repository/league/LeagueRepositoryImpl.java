package com.loljoa.server.db.repository.league;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class LeagueRepositoryImpl implements LeagueRepositoryCustom {
    private final JPAQueryFactory factory;
}