package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.GameDao;
import kr.codesquad.baseball.dto.*;
import kr.codesquad.baseball.dto.playerVO.Batter;
import kr.codesquad.baseball.dto.playerVO.Pitcher;
import kr.codesquad.baseball.dto.teamVO.DefenseTeam;
import kr.codesquad.baseball.dto.teamVO.LiveScoreOfTeamWithPlayers;
import kr.codesquad.baseball.dto.teamVO.LiveScoreOfTeam;
import kr.codesquad.baseball.dto.teamVO.OffenseTeam;
import kr.codesquad.baseball.model.Game;
import kr.codesquad.baseball.model.StatusBoard;
import kr.codesquad.baseball.model.Team;
import kr.codesquad.baseball.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<MatchListDto> findAllTypeOfMatches() {
        List<Game> allTypeOfMatches = gameDao.findAllMatches();
        return allTypeOfMatches.stream().map(match -> {
                Team awayTeam = teamService.findTeamById(match.getAwayTeam());
                Team homeTeam = teamService.findTeamById(match.getHomeTeam());
                User awayUser = userService.findUserById(match.getAwayUser());
                User homeUser = userService.findUserById(match.getHomeUser());
                return MatchListDto.builder()
                                   .matchId(match.getId())
                                   .awayTeam(awayTeam)
                                   .homeTeam(homeTeam)
                                   .awayUser(awayUser)
                                   .homeUser(homeUser)
                                   .build();
        }).collect(Collectors.toList());
    }

    public GameProgressDetailDto initializeGame(GameInitializingRequestDto initializingRequestDto) {
        Game generatedGame = insertNewGame(initializingRequestDto.getMatchId());
        teamService.initializeTeamRecordOfInning(generatedGame.getId(), generatedGame.getAwayTeam(), generatedGame.getHomeTeam(), generatedGame.getInning());
        playerService.initializePlayerRecordOfGame(generatedGame.getId(), generatedGame.getAwayTeam(), generatedGame.getHomeTeam());
        return getGameProgressDetail(generatedGame.getId(), generatedGame.getAwayTeam(), generatedGame.getHomeTeam(), generatedGame.getInning(), generatedGame.isFirsthalf());
    }

    public Game insertNewGame(int matchId) {
        Game matchType = gameDao.findMatchTypeById(matchId);
        int initializedGameId = gameDao.insertNewGame(matchType);
        return gameDao.findGameById(initializedGameId);
    }

    public GameProgressDetailDto getGameProgressDetail(int gameId, int awayTeamId, int homeTeamId, int inning, boolean isFirsthalf) {
        if (isFirsthalf) { return defineGameProgressDetailByHalfTime(gameId, awayTeamId, homeTeamId, inning); }
        return defineGameProgressDetailByHalfTime(gameId, homeTeamId, awayTeamId, inning);
    }

    private GameProgressDetailDto defineGameProgressDetailByHalfTime(int gameId, int offenseTeamId, int defenseTeamId, int inning) {
        OffenseTeam offenseTeam = teamService.findOffenseTeamByIds(gameId, offenseTeamId, inning);
        DefenseTeam defenseTeam = teamService.findDefenseTeamByIds(gameId, defenseTeamId, inning);
        User awayUser = userService.findAwayUserByGameId(gameId);
        User homeUser = userService.findHomeUserByGameId(gameId);
        StatusBoard statusBoard = createRecentStatusBoard(gameId, inning, offenseTeam, defenseTeam);
        return GameProgressDetailDto.builder()
                                    .gameId(gameId)
                                    .offenseTeam(offenseTeam)
                                    .defenseTeam(defenseTeam)
                                    .awayUser(awayUser)
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

    public GameProgressDetailDto tryPitch(GamePitchRequestDto gamePitchRequestDto) {
        Game currentGame = gameDao.findGameById(gamePitchRequestDto.getGameId());
        StatusBoard currentStatusBoard = getCurrentStatusBoard(currentGame);
        StatusBoard updatedStatusBoard = pitch(gamePitchRequestDto.getGameId(), currentStatusBoard);
        updateStatusAfterPitch(updatedStatusBoard, currentGame);
        Game updatedGame = gameDao.findGameById(currentGame.getId());
        return getGameProgressDetail(updatedGame.getId(), updatedGame.getAwayTeam(), updatedGame.getHomeTeam(), updatedGame.getInning(), updatedGame.isFirsthalf());
    }

    public StatusBoard getCurrentStatusBoard(Game game) {
        GameProgressDetailDto currentProgressDetail = getGameProgressDetail(game.getId(), game.getAwayTeam(),
                                                                            game.getHomeTeam(), game.getInning(),
                                                                            game.isFirsthalf());
        return currentProgressDetail.getStatusBoard();
    }

    public StatusBoard pitch(int gameId, StatusBoard statusBoard) {
        Batter batter = playerService.findBatterPlayerByTeamIdWithOrder(gameId, statusBoard.getTeamId(), statusBoard.getCurrentBattingOrder());
        statusBoard.executePitch(batter.getBattingAverage());
        return statusBoard;
    }

    public void updateStatusAfterPitch(StatusBoard statusBoard, Game game) {
        playerService.updatePlayerRecords(statusBoard, game);
        if (statusBoard.getOut() == 3 && statusBoard.isFirsthalf()) { teamService.updateTeamRecordToChangeOffense(statusBoard, game); }
        else if (statusBoard.getOut() == 3 && !statusBoard.isFirsthalf()) { teamService.updateTeamRecordToChangeInning(statusBoard, game); }
        else { teamService.updateTeamRecordOfCurrentInning(statusBoard, game); }
    }

    public TeamLiveScoreDto findTeamLiveScore(int gameId) {
        Game game = gameDao.findGameById(gameId);
        LiveScoreOfTeam awayTeam = teamService.findTeamLiveScoreByTeamId(gameId, game.getAwayTeam());
        LiveScoreOfTeam homeTeam = teamService.findTeamLiveScoreByTeamId(gameId, game.getHomeTeam());
        User awayUser = userService.findUserById(game.getAwayUser());
        User homeUser = userService.findUserById(game.getHomeUser());
        return TeamLiveScoreDto.builder()
                               .firsthalf(game.isFirsthalf())
                               .awayTeam(awayTeam)
                               .homeTeam(homeTeam)
                               .awayUser(awayUser)
                               .homeUser(homeUser)
                               .build();
    }

    public PlayerLiveScoreDto findPlayerLiveScore(int gameId) {
        Game game = gameDao.findGameById(gameId);
        LiveScoreOfTeamWithPlayers awayTeam = teamService.findPlayerLiveScoreByTeamId(gameId, game.getAwayTeam());
        LiveScoreOfTeamWithPlayers homeTeam = teamService.findPlayerLiveScoreByTeamId(gameId, game.getHomeTeam());
        User awayUser = userService.findUserById(game.getAwayUser());
        User homeUser = userService.findUserById(game.getHomeUser());
        return PlayerLiveScoreDto.builder()
                                 .awayTeam(awayTeam)
                                 .homeTeam(homeTeam)
                                 .awayUser(awayUser)
                                 .homeUser(homeUser)
                                 .build();
    }
}
