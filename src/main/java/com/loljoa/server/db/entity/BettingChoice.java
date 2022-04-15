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
    @GeneratedValue
    private Long choiceId;

    private String name;

    @ManyToOne(targetEntity = BettingGame.class)
    private BettingGame targetGame;

    public BettingChoice(String name, BettingGame targetGame) {
        this.name = name;
        this.targetGame = targetGame;
    }

    @OneToMany(mappedBy = "choice", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BettingState> bettingStates = new ArrayList<>();
}
