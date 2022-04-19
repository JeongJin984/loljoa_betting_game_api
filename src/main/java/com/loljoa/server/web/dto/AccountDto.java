package com.loljoa.server.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AccountDto {
    private Long accountId;
    private String username;
    List<BettingData> bettingData = new ArrayList<>();

    public AccountDto(Long accountId, String username) {
        this.accountId = accountId;
        this.username = username;
    }

    @Data
    public static class BettingData {
        private String leftTeam;
        private String rightTeam;
        private String weekNum;
        private LocalDateTime startTime;

        private String choice;
        private Long point;

        public BettingData(String leftTeam, String rightTeam, String weekNum, LocalDateTime startTime, String choice, Long point) {
            this.leftTeam = leftTeam;
            this.rightTeam = rightTeam;
            this.weekNum = weekNum;
            this.startTime = startTime;
            this.choice = choice;
            this.point = point;
        }
    }
}
