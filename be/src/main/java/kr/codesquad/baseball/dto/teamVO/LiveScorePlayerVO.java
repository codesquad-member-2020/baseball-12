package kr.codesquad.baseball.dto.teamVO;

import kr.codesquad.baseball.dto.playerVO.Batter;
import kr.codesquad.baseball.dto.playerVO.BatterLiveScoreVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LiveScorePlayerVO extends TeamVO {

    int totalPlateAppearance;

    int totalHit;

    int totalOut;

    List<BatterLiveScoreVO> playerRecords;

    @Builder
    public LiveScorePlayerVO(int teamId, String teamName, int totalScore, int score, List<BatterLiveScoreVO> playerRecords) {
        super(teamId, teamName, totalScore, score);
        this.playerRecords = playerRecords;
    }
}
