package kr.codesquad.baseball.dto;

import kr.codesquad.baseball.dto.teamVO.LiveScorePlayerVO;
import kr.codesquad.baseball.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PlayerLiveScoreDto {

    LiveScorePlayerVO awayTeam;

    LiveScorePlayerVO homeTeam;

    User awayUser;

    User homeUser;
}
