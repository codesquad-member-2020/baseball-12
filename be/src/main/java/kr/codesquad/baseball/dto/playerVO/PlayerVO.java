package kr.codesquad.baseball.dto.playerVO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter @Setter
public class PlayerVO {

    @JsonIgnore
    private Integer playerId;

    private String playerName;

    public PlayerVO() {}

    public PlayerVO(Integer playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }
}
