package kr.codesquad.baseball.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameInitializingRequestDto {

    private Integer matchId;

    public GameInitializingRequestDto() {}

    public GameInitializingRequestDto(Integer matchId) {
        this.matchId = matchId;
    }
}
