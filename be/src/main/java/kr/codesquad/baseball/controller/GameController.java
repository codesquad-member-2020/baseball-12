package kr.codesquad.baseball.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import kr.codesquad.baseball.dto.*;
import kr.codesquad.baseball.response.ApiResponse;
import kr.codesquad.baseball.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/matches")
    public ResponseEntity<ApiResponse<MatchListDto>> showAll() {
        return new ResponseEntity(ApiResponse.OK(true, gameService.findAllTypeOfMatches()), HttpStatus.OK);
    }

    @PostMapping("/matches/start")
    public ResponseEntity<ApiResponse<GameProgressDetailDto>> start(@RequestBody GameInitializingRequestDto initializingRequestDto) {
        return new ResponseEntity(ApiResponse.OK(true, gameService.initializeGame(initializingRequestDto)), HttpStatus.OK);
    }

    @PostMapping("/matches/pitch")
    public ResponseEntity<ApiResponse<GameProgressDetailDto>> pitch(@RequestBody GamePitchRequestDto gamePitchRequestDto) {
        return new ResponseEntity(ApiResponse.OK(true, gameService.tryPitch(gamePitchRequestDto)), HttpStatus.OK);
    }

    @GetMapping("/matches/{gameId}/team-score")
    public ResponseEntity<ApiResponse<TeamLiveScoreDto>> showTeamLiveScore(@PathVariable int gameId) {
        return new ResponseEntity(ApiResponse.OK(true, gameService.findTeamLiveScore(gameId)), HttpStatus.OK);
    }

    @GetMapping("/matches/{gameId}/player-score")
    public ResponseEntity<ApiResponse<PlayerLiveScoreDto>> showPlayerLiveScore(@PathVariable int gameId) {
        return new ResponseEntity(ApiResponse.OK(true, gameService.findPlayerLiveScore(gameId)), HttpStatus.OK);
    }
}
