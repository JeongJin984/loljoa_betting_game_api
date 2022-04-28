package com.loljoa.server.web.api;

import com.loljoa.server.web.dto.AccountDto;
import com.loljoa.server.web.dto.GameDataDto;
import com.loljoa.server.web.service.BettingGameService;
import com.loljoa.server.web.service.PointDistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/betting/game")
@RequiredArgsConstructor
public class BettingGameAPI {
    private final BettingGameService bettingGameService;
    private final PointDistributionService pointDistributionService;

    @GetMapping("/data")
    public List<GameDataDto> getBettingGameData(
            @RequestParam Long leagueId
    ) {
        List<GameDataDto> bettingGameData = bettingGameService.getBettingGameData(leagueId);
        return bettingGameData;
    }

    @GetMapping("/betting")
    public AccountDto.BettingData betting(
            @RequestParam Long accountId,
            @RequestParam Long choiceId,
            @RequestParam Long leagueId,
            @RequestParam Long gameId,
            @RequestParam Long point
    ) {
        return bettingGameService.bettingToChoice(choiceId, accountId, leagueId, gameId,point);
    }

    @GetMapping("/user")
    public AccountDto bettingState(
            @Nullable @RequestParam String username,
            HttpServletRequest request
    ) {
        if(StringUtils.hasText(username)) {
            return bettingGameService.getAccountBettingData(username);
        } else {
            Cookie[] cookies = request.getCookies();
            return bettingGameService.getAccountBettingData(request.getCookies()[0].getValue());
        }
    }

    @PostMapping("/cancel")
    public void cancelBetting(
            @RequestParam Long accountId,
            @RequestParam Long choiceId,
            @RequestParam Long gameId
    ) {
        bettingGameService.cancelBetting(accountId, choiceId, gameId);
    }

    @PostMapping("/distribute")
    public void distributePoint(
            @RequestParam Long gameId,
            @RequestParam Long choiceId
    ) {
        pointDistributionService.distributePoint(gameId, choiceId);
    }
}
