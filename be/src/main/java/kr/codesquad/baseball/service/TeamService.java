package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.TeamDao;
import kr.codesquad.baseball.dto.playerVO.BatterDetail;
import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import kr.codesquad.baseball.dto.playerVO.Batter;
import kr.codesquad.baseball.dto.playerVO.Pitcher;
import kr.codesquad.baseball.dto.teamVO.DefenseTeam;
import kr.codesquad.baseball.dto.teamVO.OffenseTeam;
import kr.codesquad.baseball.model.Game;
import kr.codesquad.baseball.model.StatusBoard;
import kr.codesquad.baseball.model.TeamRecord;
import org.springframework.stereotype.Service;

import java.util.List;

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
        TeamRecord offenseTeamRecord = teamDao.findTeamRecordByIds(gameId, teamId, inning);
        String teamName = teamDao.findTeamNameById(teamId);
        List<BatterSummary> onBases = playerService.findBasemanByPlayerIds(offenseTeamRecord.getFirstBaseman(),
                                                                           offenseTeamRecord.getSecondBaseman(),
                                                                           offenseTeamRecord.getThirdBaseman());
        Batter batter = playerService.findBatterPlayerByTeamIdWithOrder(teamId, currentBattingOrder);
        List<BatterDetail> battingRecords = playerService.findAllBattingRecordsOfCurrentInningByIds(gameId, teamId, offenseTeamRecord.getInning());
        return OffenseTeam.builder()
                          .teamId(teamId)
                          .teamName(teamName)
                          .score(offenseTeamRecord.getScore())
                          .onBases(onBases)
                          .batter(batter)
                          .battingRecords(battingRecords)
                          .build();
    }

    public DefenseTeam findDefenseTeamByIds(int gameId, int teamId, int inning) {
        TeamRecord defenseTeamRecord = teamDao.findTeamRecordByIds(gameId, teamId, inning);
        String teamName = teamDao.findTeamNameById(teamId);
        Pitcher pitcher = playerService.findPitcherByIds(gameId, teamId);
        return DefenseTeam.builder()
                          .teamId(teamId)
                          .teamName(teamName)
                          .score(defenseTeamRecord.getScore())
                          .pitcher(pitcher)
                          .build();
    }

    public int findCurrentBattingOrder(int gameId, int teamId, int inning) {
        if (inning > 1) return teamDao.findCurrentBattingOrderOfInning(gameId, teamId, inning - 1);
        else return teamDao.findCurrentBattingOrderOfInning(gameId, teamId, inning);
    }

    public void updateTeamRecordOfCurrentInning(StatusBoard statusBoard, Game game) {
        teamDao.updateTeamRecordOfCurrentInning(statusBoard, game);
    }

    public void updateTeamRecordToChangeOffense(StatusBoard statusBoard, Game game) {
        teamDao.updateTeamRecordOfCurrentInning(statusBoard, game);
        teamDao.updateCurrentGameInformation(statusBoard.getInning(), !game.isFirsthalf(), game.getId());
    }

    public void updateTeamRecordToChangeInning(StatusBoard statusBoard, Game game) {
        teamDao.updateTeamRecordOfCurrentInning(statusBoard, game);
        teamDao.updateCurrentGameInformation(statusBoard.getInning() + 1, game.isFirsthalf(), game.getId());
    }
}
