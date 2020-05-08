package kr.codesquad.baseball.dto.playerVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class PlayerVO {

    private int playerId;

    private String playerName;
}
