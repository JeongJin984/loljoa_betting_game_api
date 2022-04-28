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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountId;

    @Column(unique = true)
    String username;
    String password;

    Long point;

    public Account(Long accountId) {
        this.accountId = accountId;
    }

    public Account(String username, String password, Long point) {
        this.username = username;
        this.password = password;
        this.point = point;
    }

    public void usePoint(Long point) {
        this.point -= point;
    }

    public void addPoint(Long point){ this.point += point; }
}
