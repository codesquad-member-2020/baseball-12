package kr.codesquad.baseball.controller;

import kr.codesquad.baseball.dto.GameInitializingRequestDto;
import kr.codesquad.baseball.dto.GameProgressDetailDto;
import kr.codesquad.baseball.response.ApiResponse;
import kr.codesquad.baseball.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/matches/start")
    public ResponseEntity<ApiResponse<GameProgressDetailDto>> start(@RequestBody GameInitializingRequestDto initializingRequestDto) {
        GameProgressDetailDto gameProgressDetailDto = gameService.initializeGame(initializingRequestDto);
        return new ResponseEntity(ApiResponse.OK(true, gameProgressDetailDto), HttpStatus.OK);
    }

    @PostMapping("/matches/pitch")
    public ResponseEntity<ApiResponse<GameProgressDetailDto>> pitch(@RequestBody GameInitializingRequestDto initializingRequestDto) {
        return new ResponseEntity(ApiResponse.OK(true, gameService.tryPitch(initializingRequestDto)), HttpStatus.OK);
    }
}
