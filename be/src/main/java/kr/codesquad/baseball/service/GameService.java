package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.GameDao;
import kr.codesquad.baseball.dto.GameInitializingRequestDto;
import kr.codesquad.baseball.dto.GameProgressDetailDto;
import kr.codesquad.baseball.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final Logger logger = LoggerFactory.getLogger(GameService.class);

    private final GameDao gameDao;

    private final TeamService teamService;

    private final PlayerService playerService;

    public GameService(GameDao gameDao, TeamService teamService, PlayerService playerService) {
        this.gameDao = gameDao;
        this.teamService = teamService;
        this.playerService = playerService;
    }

    public GameProgressDetailDto initializeGame(GameInitializingRequestDto initializingRequestDto) {
        Game game = gameDao.findGameById(initializingRequestDto.getGameId());
        logger.debug("Game >>>>>>>>>>> {}", game);
        teamService.initializeTeamRecordOfInning(game.getId(), game.getAwayTeam(),
                                                 game.getHomeTeam(), game.getInning(), game.isFirsthalf());
        playerService.initializePlayerRecordOfGame(game.getId(), game.getAwayTeam(), game.getHomeTeam());
        return GameProgressDetailDto.builder().build();
    }

    public GameProgressDetailDto getGameProgressDetail() {

    }
}
