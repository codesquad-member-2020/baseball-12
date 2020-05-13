package kr.codesquad.baseball.dto;

import kr.codesquad.baseball.model.Team;
import kr.codesquad.baseball.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MatchListDto {

    Integer matchId;

    Team awayTeam;

    Team homeTeam;

    User awayUser;

    User homeUser;
}
