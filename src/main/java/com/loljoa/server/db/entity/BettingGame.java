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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    private String title;
    private Long totalPoint;

    @ManyToOne(targetEntity = League.class, fetch = FetchType.LAZY)
    private League league;

    public BettingGame(String title, League league) {
        this.title = title;
        this.league = league;
    }

    @OneToMany(mappedBy = "targetGame", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final List<BettingChoice> choices = new ArrayList<>();

    public void addTotalPoint(Long point) {
        totalPoint += point;
    }
    public void bettingCanceled(Long point) { totalPoint -= point; }
}
