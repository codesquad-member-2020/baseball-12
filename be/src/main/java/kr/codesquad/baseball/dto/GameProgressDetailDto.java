package kr.codesquad.baseball.dto;

import kr.codesquad.baseball.dto.teamVO.DefenseTeam;
import kr.codesquad.baseball.dto.teamVO.OffenseTeam;
import kr.codesquad.baseball.model.StatusBoard;
import kr.codesquad.baseball.model.User;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class GameProgressDetailDto {

    int gameId;

    StatusBoard statusBoard;

    OffenseTeam offenseTeam;

    DefenseTeam defenseTeam;

    User awayUser;

    User homeUser;
}
