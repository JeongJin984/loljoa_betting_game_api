package com.loljoa.server.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class GameTeam {
    @Id @GeneratedValue
    private Long teamId;

    @Column(unique = true)
    private String name;

    public GameTeam(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "leftTeam")
    private List<League> leftLeagueList = new ArrayList<>();

    @OneToMany(mappedBy = "rightTeam")
    private List<League> rightLeagueList = new ArrayList<>();
}
