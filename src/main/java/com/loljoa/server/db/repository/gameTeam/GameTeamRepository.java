package com.loljoa.server.db.repository.gameTeam;

import com.loljoa.server.db.entity.GameTeam;
import com.loljoa.server.db.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTeamRepository extends CommonRepository<GameTeam, Long>, GameTeamRepositoryCustom {
}
