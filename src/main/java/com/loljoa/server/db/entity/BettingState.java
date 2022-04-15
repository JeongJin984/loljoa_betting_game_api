package com.loljoa.server.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class BettingState {
    @Id
    @GeneratedValue
    Long stateId;

    private Long point;

    @ManyToOne(targetEntity = BettingChoice.class, fetch = FetchType.LAZY)
    private BettingChoice choice;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    private Account better;

    public BettingState(BettingChoice choice, Account better, Long point) {
        this.choice = choice;
        this.better = better;
        this.point = point;
    }
}
