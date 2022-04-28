package com.loljoa.server.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class BettingState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long stateId;

    private Long point;

    @ManyToOne(targetEntity = BettingChoice.class, fetch = FetchType.LAZY)
    private BettingChoice choice;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    private Account better;

    @ManyToOne(targetEntity = League.class, fetch = FetchType.LAZY)
    private League league;

    public BettingState(BettingChoice choice, Account better, League league, Long point) {
        this.choice = choice;
        this.better = better;
        this.point = point;
        this.league = league;
    }
}
