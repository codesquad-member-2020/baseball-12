package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.GameDao;
import kr.codesquad.baseball.dto.GameInitializingRequestDto;
import kr.codesquad.baseball.dto.GameProgressDetailDto;
import kr.codesquad.baseball.dto.playerVO.Batter;
import kr.codesquad.baseball.dto.playerVO.Pitcher;
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

    public GameService(GameDao gameDao, TeamService teamService, PlayerService playerService, UserService userService) {
        this.gameDao = gameDao;
        this.teamService = teamService;
        this.playerService = playerService;
        this.userService = userService;
    }

    public GameProgressDetailDto initializeGame(GameInitializingRequestDto initializingRequestDto) {
        Game game = gameDao.findGameById(initializingRequestDto.getGameId());
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
        StatusBoard statusBoard = createRecentStatusBoard(gameId, inning, offenseTeam, defenseTeam);
        return GameProgressDetailDto.builder()
                                    .offenseTeam(offenseTeam)
                                    .defenseTeam(defenseTeam)
                                    .awayuser(awayUser)
                                    .homeUser(homeUser)
                                    .statusBoard(statusBoard)
                                    .build();
    }

    public StatusBoard createRecentStatusBoard(int gameId, int inning, OffenseTeam offenseTeam, DefenseTeam defenseTeam) {
        Game game = gameDao.findGameById(gameId);
        Batter currentBatter = offenseTeam.getBatter();
        Pitcher currentPitcher = defenseTeam.getPitcher();
        StatusBoard statusBoard = playerService.findRecentStatusOfInningByGameId(gameId, inning);
        statusBoard.setTeamId(offenseTeam.getTeamId());
        statusBoard.setBatterId(currentBatter.getPlayerId());
        statusBoard.setPitcherId(currentPitcher.getPlayerId());
        statusBoard.setFirsthalf(game.isFirsthalf());
        statusBoard.setCurrentBattingOrder(currentBatter.getBattingOrder());
        statusBoard.setScore(offenseTeam.getScore());
        statusBoard.setOnBases(offenseTeam.getOnBases());
        return statusBoard;
    }

    public GameProgressDetailDto tryPitch(GameInitializingRequestDto initializingRequestDto) {
        Game game = gameDao.findGameById(initializingRequestDto.getGameId());
        StatusBoard currentStatusBoard = getCurrentStatusBoard(game);
        StatusBoard updatedStatusBoard = pitch(currentStatusBoard);
        updateStatusAfterPitch(updatedStatusBoard, game);
        return getGameProgressDetail(game.getId(), game.getAwayTeam(), game.getHomeTeam(), game.getInning(), game.isFirsthalf());
    }

    public StatusBoard getCurrentStatusBoard(Game game) {
        GameProgressDetailDto currentProgressDetail = getGameProgressDetail(game.getId(), game.getAwayTeam(),
                                                                            game.getHomeTeam(), game.getInning(),
                                                                            game.isFirsthalf());
        return currentProgressDetail.getStatusBoard();
    }

    public StatusBoard pitch(StatusBoard statusBoard) {
        Batter batter = playerService.findBatterPlayerByTeamIdWithOrder(statusBoard.getTeamId(), statusBoard.getCurrentBattingOrder());
        statusBoard.executePitch(batter.getBattingAverage());
        return statusBoard;
    }

    public void updateStatusAfterPitch(StatusBoard statusBoard, Game game) {
        playerService.updatePlayerRecords(statusBoard, game);
        if (statusBoard.getOut() == 3 && statusBoard.isFirsthalf()) teamService.updateTeamRecordToChangeOffense(statusBoard, game);
        else if (statusBoard.getOut() == 3 && !statusBoard.isFirsthalf()) teamService.updateTeamRecordToChangeInning(statusBoard, game);
        else teamService.updateTeamRecordOfCurrentInning(statusBoard, game);
    }
}
