package kr.codesquad.baseball.service;

import kr.codesquad.baseball.commonconstant.Judgement;
import kr.codesquad.baseball.dao.TeamDao;
import kr.codesquad.baseball.dto.playerVO.*;
import kr.codesquad.baseball.dto.teamVO.*;
import kr.codesquad.baseball.model.Game;
import kr.codesquad.baseball.model.StatusBoard;
import kr.codesquad.baseball.model.Team;
import kr.codesquad.baseball.model.TeamRecord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamDao teamDao;

    private final PlayerService playerService;

    public TeamService(TeamDao teamDao, PlayerService playerService) {
        this.teamDao = teamDao;
        this.playerService = playerService;
    }

    public void initializeTeamRecordOfInning(int gameId, int awayTeamId, int homeTeamId, int nextInning) {
        teamDao.insertTeamRecordOfInning(gameId, awayTeamId, nextInning);
        teamDao.insertTeamRecordOfInning(gameId, homeTeamId, nextInning);
    }

    public OffenseTeam findOffenseTeamByIds(int gameId, int teamId, int inning) {
        int currentBattingOrder = findCurrentBattingOrder(gameId, teamId, inning);
        int totalScore = findTotalScoreOfTeam(gameId, teamId);
        double battingAverage = playerService.findBattingAverage(teamId, currentBattingOrder);
        TeamRecord offenseTeamRecord = teamDao.findTeamRecordByIds(gameId, teamId, inning);
        String teamName = teamDao.findTeamNameById(teamId);
        List<BatterSummary> onBases = playerService.findBasemanByPlayerIds(offenseTeamRecord.getFirstBaseman(),
                                                                           offenseTeamRecord.getSecondBaseman(),
                                                                           offenseTeamRecord.getThirdBaseman());
        Batter batter = playerService.findBatterPlayerByTeamIdWithOrder(gameId, teamId, currentBattingOrder);
        batter.setBattingAverage(battingAverage);
        List<BatterDetail> battingRecords = playerService.findAllBattingRecordsOfCurrentInningByIds(gameId, teamId, offenseTeamRecord.getInning());
        return OffenseTeam.builder()
                          .teamId(teamId)
                          .teamName(teamName)
                          .totalScore(totalScore)
                          .score(offenseTeamRecord.getScore())
                          .onBases(onBases)
                          .batter(batter)
                          .battingRecords(battingRecords)
                          .build();
    }

    public int findTotalScoreOfTeam(int gameId, int teamId) {
       return teamDao.findTotalScoreOfTeam(gameId, teamId);
    }

    public DefenseTeam findDefenseTeamByIds(int gameId, int teamId, int inning) {
        int totalScore = teamDao.findTotalScoreOfTeam(gameId, teamId);
        TeamRecord defenseTeamRecord = teamDao.findTeamRecordByIds(gameId, teamId, inning);
        String teamName = teamDao.findTeamNameById(teamId);
        Pitcher pitcher = playerService.findPitcherByIds(gameId, teamId);
        return DefenseTeam.builder()
                          .teamId(teamId)
                          .teamName(teamName)
                          .totalScore(totalScore)
                          .score(defenseTeamRecord.getScore())
                          .pitcher(pitcher)
                          .build();
    }

    public int findCurrentBattingOrder(int gameId, int teamId, int inning) {
        return teamDao.findCurrentBattingOrderOfInning(gameId, teamId, inning);
    }

    public void updateTeamRecordOfCurrentInning(StatusBoard statusBoard, Game game) {
        teamDao.updateTeamRecordOfCurrentInning(statusBoard, game);
    }

    public void updateTeamRecordToChangeOffense(StatusBoard statusBoard, Game game) {
        int currentBattingOrder;
        int currentInning = statusBoard.getInning();
        playerService.updatePlayerRecordsForChange(statusBoard, game);
        teamDao.updateTeamRecordOfCurrentInning(statusBoard, game);
        teamDao.updateCurrentGameInformation(currentInning, !game.isFirsthalf(), game.getId());
        if (currentInning > 1) { currentBattingOrder = teamDao.findCurrentBattingOrderOfInning(game.getId(), game.getHomeTeam(), currentInning - 1); }
        else { currentBattingOrder = teamDao.findCurrentBattingOrderOfInning(game.getId(), game.getHomeTeam(), currentInning); }
        teamDao.updateCurrentBattingOrderOfInning(game.getId(), game.getHomeTeam(), statusBoard.getInning(), currentBattingOrder);
    }

    public void updateTeamRecordToChangeInning(StatusBoard statusBoard, Game game) {
        teamDao.updateTeamRecordOfCurrentInning(statusBoard, game);
        teamDao.updateCurrentGameInformation(statusBoard.getInning() + 1, !game.isFirsthalf(), game.getId());
        initializeTeamRecordOfInning(game.getId(), game.getAwayTeam(), game.getHomeTeam(), game.getInning() + 1);
        int currentBattingOrder = teamDao.findCurrentBattingOrderOfInning(game.getId(), game.getAwayTeam(), statusBoard.getInning());
        teamDao.updateCurrentBattingOrderOfInning(game.getId(), game.getAwayTeam(), statusBoard.getInning() + 1, currentBattingOrder);
    }

    public Team findTeamById(int teamId) {
        return teamDao.findTeamById(teamId);
    }

    public LiveScoreOfTeam findTeamLiveScoreByTeamId(int gameId, int teamId) {
        Team team = findTeamById(teamId);
        List<Integer> scores = teamDao.findScoreListOfAllInningByIds(gameId, teamId);
        return LiveScoreOfTeam.builder()
                              .teamId(team.getId())
                              .teamName(team.getName())
                              .totalScore(teamDao.findTotalScoreOfTeam(gameId, teamId))
                              .scores(scores)
                              .build();
    }

    public LiveScoreOfTeamWithPlayers findPlayerLiveScoreByTeamId(int gameId, int teamId) {
        Team team = findTeamById(teamId);
        List<Integer> playerIds = playerService.findPlayerIdsByTeamId(teamId);
        List<BatterLiveScoreVO> playerRecords = playerIds.stream().map(playerId -> {
            Batter batter = playerService.findBatterPlayerOfCurrentGameByIds(gameId, teamId, playerId);
            double totalHitCountOfBatterInGame = batter.getHitCount();
            double totalPlateAppearanceOfBatterInGame = batter.getPlateAppearance();
            double battingAverageOfBatterInGame = totalHitCountOfBatterInGame / totalPlateAppearanceOfBatterInGame;
            int totalOutCountOfBatterInGame = playerService.findTotalJudgementCountOfPlayerInGameByIds(gameId, playerId, Judgement.OUT);
            return BatterLiveScoreVO.builder()
                                    .playerId(batter.getPlayerId())
                                    .playerName(batter.getPlayerName())
                                    .battingOrder(batter.getBattingOrder())
                                    .battingAverage(battingAverageOfBatterInGame)
                                    .plateAppearance(batter.getPlateAppearance())
                                    .hitCount(batter.getHitCount())
                                    .outCount(totalOutCountOfBatterInGame)
                                    .build();
        }).collect(Collectors.toList());
        int totalPlateAppearanceOfTeam = playerRecords.stream()
                                        .map(batter -> batter.getPlateAppearance())
                                        .reduce(0, (totalPlateAppearance, plateAppearance) -> totalPlateAppearance += plateAppearance);
        int totalHitCountOfTeam = playerRecords.stream()
                                        .map(batter -> batter.getHitCount())
                                        .reduce(0, (totalHitCount, hitCount) -> totalHitCount += hitCount);
        int totalOutCountOfTeam = playerRecords.stream()
                                        .map(batter -> batter.getOutCount())
                                        .reduce(0, (totalOutCount, outCount) -> totalOutCount += outCount);
        return LiveScoreOfTeamWithPlayers.playerLiveScoreBuilder()
                                         .totalPlateAppearance(totalPlateAppearanceOfTeam)
                                         .totalHit(totalHitCountOfTeam)
                                         .totalOut(totalOutCountOfTeam)
                                         .playerRecords(playerRecords)
                                         .build();
    }
}
