package kr.codesquad.baseball.dto;

import kr.codesquad.baseball.dto.teamVO.LiveScoreTeamVO;
import kr.codesquad.baseball.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TeamLiveScoreDto {

    boolean firsthalf;

    LiveScoreTeamVO awayTeam;

    LiveScoreTeamVO homeTeam;

    User awayUser;

    User homeUser;
}
