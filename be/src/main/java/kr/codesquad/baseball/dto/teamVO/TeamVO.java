package kr.codesquad.baseball.dto.teamVO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TeamVO {

    @JsonIgnore
    private int teamId;

    private String teamName;

    private int totalScore;

    @JsonIgnore
    private int score;
}
