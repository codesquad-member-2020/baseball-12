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
//        List<Integer> playerIds = playerDao.findPlayerIdsByTeamId(teamId);
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
        return playerDao.findRecentPlayerRecordOfInningByGameId(gameId, inning);
    }

    public void updatePlayerRecords(StatusBoard statusBoard, Game game) {
        playerDao.updatePlayerRecordOfBatter(statusBoard.getBatterId(), statusBoard.getAddedHitCount(), game.getId());
        playerDao.updatePlayerRecordOfPitcher(statusBoard.getPitcherId(), game.getId());
        playerDao.insertBattingRecord(statusBoard, game);
    }
}
