package kr.codesquad.baseball.dto.playerVO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pitcher extends PlayerVO {

    private int pitchingCount;

    @Builder(builderMethodName = "pitcherBuilder")
    public Pitcher(int playerId, String playerName, int pitchingCount) {
        super(playerId, playerName);
        this.pitchingCount = pitchingCount;
    }
}
