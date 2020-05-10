package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.GameDao;
import kr.codesquad.baseball.dto.GameInitializingRequestDto;
import kr.codesquad.baseball.dto.GameProgressDetailDto;
import kr.codesquad.baseball.dto.teamVO.DefenseTeam;
import kr.codesquad.baseball.dto.teamVO.OffenseTeam;
import kr.codesquad.baseball.model.Game;
import kr.codesquad.baseball.model.StatusBoard;
import kr.codesquad.baseball.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final Logger logger = LoggerFactory.getLogger(GameService.class);

    private final GameDao gameDao;

    private final TeamService teamService;

    private final PlayerService playerService;

    private final UserService userService;

    public static class Status {
        public static String judgement;
        public static int inning;
        public static int strike;
        public static int ball;
        public static int hit;
        public static int out;
    }

    public GameService(GameDao gameDao, TeamService teamService, PlayerService playerService, UserService userService) {
        this.gameDao = gameDao;
        this.teamService = teamService;
        this.playerService = playerService;
        this.userService = userService;
    }

    public GameProgressDetailDto initializeGame(GameInitializingRequestDto initializingRequestDto) {
        Game game = gameDao.findGameById(initializingRequestDto.getGameId());
        logger.debug("Game >>>>>>>>>>> {}", game);
        teamService.initializeTeamRecordOfInning(game.getId(), game.getAwayTeam(), game.getHomeTeam(), game.getInning());
        playerService.initializePlayerRecordOfGame(game.getId(), game.getAwayTeam(), game.getHomeTeam());
        return getGameProgressDetail(game.getId(), game.getAwayTeam(), game.getHomeTeam(), game.getInning(), game.isFirsthalf());
    }

    public GameProgressDetailDto getGameProgressDetail(int gameId, int awayTeamId, int homeTeamId, int inning, boolean isFirsthalf) {
        if (isFirsthalf) return defineGameProgressDetailByHalfTime(gameId, awayTeamId, homeTeamId, inning);
        return defineGameProgressDetailByHalfTime(gameId, homeTeamId, awayTeamId, inning);
    }

    private GameProgressDetailDto defineGameProgressDetailByHalfTime(int gameId, int offenseTeamId, int defenseTeamId, int inning) {
        OffenseTeam offenseTeam = teamService.findOffenseTeamByIds(gameId, offenseTeamId, inning);
        DefenseTeam defenseTeam = teamService.findDefenseTeamByIds(gameId, defenseTeamId, inning);
        User awayUser = userService.findAwayUserByGameId(gameId);
        User homeUser = userService.findHomeUserByGameId(gameId);
        return GameProgressDetailDto.builder()
                                    .offenseTeam(offenseTeam)
                                    .defenseTeam(defenseTeam)
                                    .awayuser(awayUser)
                                    .homeUser(homeUser)
                                    .statusBoard(new StatusBoard(Status.judgement, Status.inning, Status.strike, Status.ball, Status.hit, Status.out))
                                    .build();
    }

    public GameProgressDetailDto tryPitching(GameInitializingRequestDto initializingRequestDto) {
        Game game = gameDao.findGameById(initializingRequestDto.getGameId());

    }
}
