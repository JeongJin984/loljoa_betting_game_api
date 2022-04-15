package com.loljoa.server.db.repository.gameTeam;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GameTeamRepositoryImpl {
    private final JPAQueryFactory factory;
}
