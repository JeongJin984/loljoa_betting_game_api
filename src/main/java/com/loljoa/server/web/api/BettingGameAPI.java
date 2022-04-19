package com.loljoa.server.web.api;

import com.loljoa.server.web.dto.GameDataDto;
import com.loljoa.server.web.service.BettingGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/betting/game")
@RequiredArgsConstructor
public class BettingGameAPI {
    private final BettingGameService bettingGameService;

    @GetMapping("/data")
    public List<GameDataDto> getBettingGameData(
            @RequestParam Long leagueId
    ) {
        List<GameDataDto> bettingGameData = bettingGameService.getBettingGameData(leagueId);
        return bettingGameData;
    }

    @GetMapping("/betting")
    public void bettingState(
            @RequestParam Long accountId,
            @RequestParam Long choiceId,
            @RequestParam Long point
    ) {
        bettingGameService.bettingToChoice(choiceId, accountId, point);
    }

    @GetMapping("/user")
    public void bettingState(
            @RequestParam Long accountId
    ) {
        bettingGameService.getAccountBettingData(accountId);
    }

}
