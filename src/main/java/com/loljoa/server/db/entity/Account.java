package com.loljoa.server.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    Long accountId;

    @Column(unique = true)
    String userId;

    @Column(unique = true)
    String nickname;
    String password;

    @OneToMany(mappedBy = "better")
    List<BettingState> bettingStates = new ArrayList<>();

    public Account(Long accountId) {
        this.accountId = accountId;
    }

    public Account(String userId, String nickname, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }
}
