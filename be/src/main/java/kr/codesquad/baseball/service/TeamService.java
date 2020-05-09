package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.TeamDao;
import kr.codesquad.baseball.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamDao teamDao;

    public TeamService(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public void initializeTeamRecordOfInning(int gameId, int awayTeamId, int homeTeamId, int nextInning, boolean isFirsthalf) {
        if (isFirsthalf) teamDao.insertTeamRecordOfInning(gameId, awayTeamId, nextInning);
        else teamDao.insertTeamRecordOfInning(gameId, homeTeamId, nextInning);
    }


}
