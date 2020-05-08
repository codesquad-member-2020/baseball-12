package kr.codesquad.baseball.dto.teamVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TeamVO {

    private int teamId;

    private String teamName;

    private int score;
}
