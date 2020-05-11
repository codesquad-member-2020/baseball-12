package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.PlayerDao;
import kr.codesquad.baseball.dto.playerVO.BatterDetail;
import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import kr.codesquad.baseball.dto.playerVO.Batter;
import kr.codesquad.baseball.dto.playerVO.Pitcher;
import kr.codesquad.baseball.model.BattingRecord;
import kr.codesquad.baseball.model.Game;
import kr.codesquad.baseball.model.StatusBoard;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void initializePlayerRecordOfGame(int gameId, int awayTeamId, int homeTeamId) {
        List<Integer> awayPlayerIds = playerDao.findPlayerIdsByTeamId(awayTeamId);
        List<Integer> homePlayerIds = playerDao.findPlayerIdsByTeamId(homeTeamId);
        awayPlayerIds.forEach(awayId -> playerDao.insertPlayerRecord(gameId, awayId));
        homePlayerIds.forEach(homeId -> playerDao.insertPlayerRecord(gameId, homeId));
    }

    public List<BatterSummary> findBasemanByPlayerIds(Integer firstBaseman, Integer secondBaseman, Integer thirdBaseman) {
        List<Integer> basemanIds = Arrays.asList(firstBaseman, secondBaseman, thirdBaseman);
        return basemanIds.stream().map(playerDao::findBatterSummaryById).collect(Collectors.toList());
    }

    public Batter findBatterPlayerByTeamIdWithOrder(int teamId, int currentBattingOrder) {
        return playerDao.findBatterPlayerByTeamIdWithOrder(teamId, currentBattingOrder);
    }

    public List<BatterDetail> findAllBattingRecordsOfCurrentInningByIds(int gameId, int teamId, int currentInning) {
        List<Integer> recordedPlayerIds = playerDao.findRecordedPlayerIds(gameId, teamId, currentInning);
        return recordedPlayerIds.stream()
                .map((playerId) -> {
                    BatterSummary batterSummary = playerDao.findBatterSummaryById(playerId);
                    List<BattingRecord> battings = playerDao.findBattingRecordsOfInningByIds(gameId, playerId, currentInning);
                    return BatterDetail.batterDetailBuilder()
                                       .playerId(batterSummary.getPlayerId())
                                       .playerName(batterSummary.getPlayerName())
                                       .battingOrder(batterSummary.getBattingOrder())
                                       .battings(battings)
                                       .build();
                }).collect(Collectors.toList());
    }

    public Pitcher findPitcherByIds(int gameId, int teamId) {
        return playerDao.findPitcherByIds(gameId, teamId);
    }

    public StatusBoard findRecentStatusOfInningByGameId(int gameId, int inning) {
        return Optional.ofNullable(playerDao.findRecentPlayerRecordOfInningByGameId(gameId, inning))
                       .orElse(StatusBoard.builder()
                                          .judgement("")
                                          .inning(inning)
                                          .strike(0)
                                          .ball(0)
                                          .hit(0)
                                          .out(0)
                                          .build());
    }

    public void updatePlayerRecords(StatusBoard statusBoard, Game game) {
        playerDao.updatePlayerRecordOfBatter(statusBoard.getBatterId(), statusBoard.getAddedHitCount(), game.getId());
        playerDao.updatePlayerRecordOfPitcher(statusBoard.getPitcherId(), game.getId());
        playerDao.insertBattingRecord(statusBoard.getBatterId(), statusBoard.getInning(), statusBoard.getJudgement(),
                                      statusBoard.getStrike(), statusBoard.getBall(), statusBoard.getHit(),
                                      statusBoard.getOut(), game.getId());
    }

    public void updatePlayerRecordsForChange(StatusBoard statusBoard, Game game) {
        String initializedJudgement = "";
        int initializedStrike = 0; int initializedBall = 0;
        int initializedHit = 0;    int initializedOut = 0;
        playerDao.insertBattingRecord(statusBoard.getBatterId(), statusBoard.getInning(),
                                      initializedJudgement, initializedStrike, initializedBall, initializedHit, initializedOut, game.getId());
    }
}
