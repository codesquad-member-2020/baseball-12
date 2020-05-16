package kr.codesquad.baseball.dto.teamVO;

import kr.codesquad.baseball.dto.playerVO.Pitcher;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DefenseTeam extends TeamVO {

    private Pitcher pitcher;

    @Builder
    public DefenseTeam(Integer teamId, String teamName, int totalScore, int score, Pitcher pitcher) {
        super(teamId, teamName, totalScore, score);
        this.pitcher = pitcher;
    }
}
