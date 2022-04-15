package com.loljoa.server.db.repository.bettingGame;

import com.loljoa.server.db.entity.BettingGame;
import com.loljoa.server.db.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BettingGameRepository extends CommonRepository<BettingGame, Long>, BettingGameRepositoryCustom {
}
