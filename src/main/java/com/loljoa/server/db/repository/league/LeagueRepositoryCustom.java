package com.loljoa.server.db.repository.league;

import com.loljoa.server.db.entity.League;

public interface LeagueRepositoryCustom {
    League getLeagueById(Long leagueId);
}