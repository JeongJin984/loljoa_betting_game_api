package com.loljoa.server.web.api;

import com.loljoa.server.web.dto.AccountDto;
import com.loljoa.server.web.dto.GameDataDto;
import com.loljoa.server.web.service.BettingGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public AccountDto.BettingData bettingState(
            @RequestParam Long accountId,
            @RequestParam Long choiceId,
            @RequestParam Long leagueId,
            @RequestParam Long point
    ) {
        return bettingGameService.bettingToChoice(choiceId, accountId, leagueId, point);
    }

    @GetMapping("/user")
    public AccountDto bettingState(
            HttpServletRequest request
    ) {
        Cookie[] cookies = request.getCookies();
        return bettingGameService.getAccountBettingData(request.getCookies()[0].getValue());
    }
}
