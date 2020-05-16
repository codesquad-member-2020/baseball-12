package kr.codesquad.baseball.dto.teamVO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeamVO {

    @JsonIgnore
    private int teamId;

    private String teamName;

    private int totalScore;

    @JsonIgnore
    private int score;

    public TeamVO() {}

    public TeamVO(Integer teamId, String teamName, int totalScore, int score) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.totalScore = totalScore;
        this.score = score;
    }
}
