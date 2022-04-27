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
    private Long point;
    List<BettingData> bettingData = new ArrayList<>();

    public AccountDto(Long accountId, String username, Long point) {
        this.accountId = accountId;
        this.username = username;
        this.point = point;
    }

    @Data
    public static class BettingData {
        private Long gameId;

        private String leftTeam;
        private String rightTeam;
        private String weekNum;
        private LocalDateTime startTime;

        private Long choiceId;
        private String choice;
        private Long choiceTotalPoint;
        private String odd;
        private Long point;

        public BettingData(
                Long gameId,
                String leftTeam,
                String rightTeam,
                String weekNum,
                LocalDateTime startTime,
                Long choiceId,
                String choice,
                Long choiceTotalPoint,
                Long totalGamePoint,
                Long point
        ) {
            this.gameId = gameId;
            this.leftTeam = leftTeam;
            this.rightTeam = rightTeam;
            this.weekNum = weekNum;
            this.startTime = startTime;
            this.choiceId = choiceId;
            this.choice = choice;
            this.choiceTotalPoint = choiceTotalPoint;
            this.odd = String.format("%.2f", ( totalGamePoint / choiceTotalPoint.doubleValue()));
            this.point = point;
        }
    }
}
