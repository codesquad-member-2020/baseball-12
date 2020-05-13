package kr.codesquad.baseball.dto.teamVO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LiveScoreOfTeam extends TeamVO {

    List<Integer> scores;

    @Builder
    public LiveScoreOfTeam(int teamId, String teamName, int totalScore, int score, List<Integer> scores) {
        super(teamId, teamName, totalScore, score);
        this.scores = scores;
    }
}
