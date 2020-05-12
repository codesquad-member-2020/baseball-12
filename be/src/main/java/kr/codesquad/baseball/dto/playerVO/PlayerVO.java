package kr.codesquad.baseball.dto.playerVO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class PlayerVO {

    @JsonIgnore
    private int playerId;

    private String playerName;
}
