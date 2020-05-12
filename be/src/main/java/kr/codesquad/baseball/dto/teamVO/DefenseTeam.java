package kr.codesquad.baseball.dto.teamVO;

import kr.codesquad.baseball.dto.playerVO.Pitcher;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DefenseTeam extends TeamVO {

    private Pitcher pitcher;

    @Builder
    public DefenseTeam(int teamId, String teamName, int score, Pitcher pitcher) {
        super(teamId, teamName, score);
        this.pitcher = pitcher;
    }
}
