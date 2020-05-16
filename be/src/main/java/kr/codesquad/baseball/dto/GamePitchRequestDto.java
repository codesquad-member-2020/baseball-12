package kr.codesquad.baseball.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GamePitchRequestDto {

    private Integer gameId;

    public GamePitchRequestDto() {}

    public GamePitchRequestDto(Integer gameId) {
        this.gameId = gameId;
    }
}
