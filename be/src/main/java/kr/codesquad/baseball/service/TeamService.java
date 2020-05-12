package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.TeamDao;
import kr.codesquad.baseball.dto.playerVO.BatterDetail;
import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import kr.codesquad.baseball.dto.playerVO.Batter;
import kr.codesquad.baseball.dto.playerVO.Pitcher;
import kr.codesquad.baseball.dto.teamVO.DefenseTeam;
import kr.codesquad.baseball.dto.teamVO.OffenseTeam;
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

    public void initializeTeamRecordOfInning(int gameId, int awayTeamId, int homeTeamId, int nextInning, boolean isFirsthalf) {
        teamDao.insertTeamRecordOfInning(gameId, awayTeamId, nextInning);
        teamDao.insertTeamRecordOfInning(gameId, homeTeamId, nextInning);
    }

    public OffenseTeam findOffenseTeamByIds(int gameId, int teamId, int inning) {
        TeamRecord offenseTeamRecord = teamDao.findTeamRecordByIds(gameId, teamId, inning);
        String teamName = teamDao.findTeamNameById(teamId);
        List<BatterSummary> onBases = playerService.findBasemanByPlayerIds(offenseTeamRecord.getFirstBaseman(),
                                                                           offenseTeamRecord.getSecondBaseman(),
                                                                           offenseTeamRecord.getThirdBaseman());
        Batter batter = playerService.findBatterPlayerByTeamIdWithOrder(teamId, offenseTeamRecord.getCurrentBattingOrder());
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
}
