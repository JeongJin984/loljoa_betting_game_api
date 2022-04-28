package com.loljoa.server.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class BettingChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long choiceId;

    private String name;
    private Long totalPoint;
    private Long biggestPoint;
    private String biggestBetter;

    @ManyToOne(targetEntity = BettingGame.class, fetch = FetchType.LAZY)
    private BettingGame targetGame;

    public BettingChoice(String name, BettingGame targetGame) {
        this.name = name;
        this.targetGame = targetGame;
    }

    @OneToMany(mappedBy = "choice", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BettingState> bettingStates = new ArrayList<>();

    public void addTotalPoint(String better, Long point) {
        this.totalPoint += point;
        if(point > biggestPoint) {
            this.biggestBetter = better;
            this.biggestPoint = point;
        }
    }

    public void bettingCanceled(Long point) {
        this.totalPoint -= point;
    }
}
