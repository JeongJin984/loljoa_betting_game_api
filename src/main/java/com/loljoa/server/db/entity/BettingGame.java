package com.loljoa.server.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class BettingGame {
    @Id
    @GeneratedValue
    private Long gameId;

    private String title;

    @ManyToOne(targetEntity = League.class)
    private League league;

    public BettingGame(String title, League league) {
        this.title = title;
        this.league = league;
    }

    @OneToMany(mappedBy = "targetGame")
    private final List<BettingChoice> choices = new ArrayList<>();
}
