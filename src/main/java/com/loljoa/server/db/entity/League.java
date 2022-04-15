package com.loljoa.server.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class League {
    @Id
    @GeneratedValue
    private Long leagueId;

    private String weekNum;
    private String leagueName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(targetEntity = LeagueCategory.class)
    private LeagueCategory category;

    @ManyToOne(targetEntity = GameTeam.class)
    private GameTeam leftTeam;

    @ManyToOne(targetEntity = GameTeam.class)
    private GameTeam rightTeam;

    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    private List<BettingGame> bettingGameData = new ArrayList<>();

    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    private List<LeagueComment> comments = new ArrayList<>();
}
