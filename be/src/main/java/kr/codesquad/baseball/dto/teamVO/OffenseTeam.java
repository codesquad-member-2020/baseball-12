package kr.codesquad.baseball.dto.teamVO;

import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import kr.codesquad.baseball.dto.playerVO.BatterDetail;
import kr.codesquad.baseball.dto.playerVO.Batter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OffenseTeam extends TeamVO {

    private List<BatterSummary> onBases;

    private Batter batter;

    private List<BatterDetail> battingRecords;

    @Builder
    public OffenseTeam(int teamId, String teamName, int totalScore, int score, List<BatterSummary> onBases, Batter batter, List<BatterDetail> battingRecords) {
        super(teamId, teamName, totalScore, score);
        this.onBases = onBases;
        this.batter = batter;
        this.battingRecords = battingRecords;
    }
}
