package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.PlayerDao;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
