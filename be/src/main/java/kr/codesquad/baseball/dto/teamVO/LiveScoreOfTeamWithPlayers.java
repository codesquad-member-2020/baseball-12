package kr.codesquad.baseball.dto.teamVO;

import kr.codesquad.baseball.dto.playerVO.BatterLiveScoreVO;
import kr.codesquad.baseball.model.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LiveScoreOfTeamWithPlayers extends Team {

    int totalPlateAppearance;

    int totalHit;

    int totalOut;

    List<BatterLiveScoreVO> playerRecords;

    @Builder(builderMethodName = "playerLiveScoreBuilder")
    public LiveScoreOfTeamWithPlayers(Integer teamId, String teamName, int score, int totalPlateAppearance, int totalHit, int totalOut, List<BatterLiveScoreVO> playerRecords) {
        super(teamId, teamName, score);
        this.totalPlateAppearance = totalPlateAppearance;
        this.totalHit = totalHit;
        this.totalOut = totalOut;
        this.playerRecords = playerRecords;
    }
}
