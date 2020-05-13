package kr.codesquad.baseball.dto;

import kr.codesquad.baseball.dto.teamVO.LiveScoreOfTeam;
import kr.codesquad.baseball.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TeamLiveScoreDto {

    boolean firsthalf;

    LiveScoreOfTeam awayTeam;

    LiveScoreOfTeam homeTeam;

    User awayUser;

    User homeUser;
}
