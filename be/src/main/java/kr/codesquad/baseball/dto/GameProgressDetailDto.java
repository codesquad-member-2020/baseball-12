package kr.codesquad.baseball.dto;

import kr.codesquad.baseball.dto.teamVO.DefenseTeam;
import kr.codesquad.baseball.dto.teamVO.OffenseTeam;
import kr.codesquad.baseball.model.StatusBoard;
import kr.codesquad.baseball.model.User;
import lombok.*;

@Getter @Setter
@Builder
public class GameProgressDetailDto {

    int gameId;

    StatusBoard statusBoard;

    OffenseTeam offenseTeam;

    DefenseTeam defenseTeam;

    User awayUser;

    User homeUser;

    public GameProgressDetailDto(int gameId, StatusBoard statusBoard, OffenseTeam offenseTeam, DefenseTeam defenseTeam, User awayUser, User homeUser) {
        this.gameId = gameId;
        this.statusBoard = statusBoard;
        this.offenseTeam = offenseTeam;
        this.defenseTeam = defenseTeam;
        this.awayUser = awayUser;
        this.homeUser = homeUser;
    }
}
