package kr.codesquad.baseball.dto.playerVO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BatterSummary extends PlayerVO {

    private int battingOrder;

    @Builder
    public BatterSummary(int playerId, String playerName, int battingOrder) {
        super(playerId, playerName);
        this.battingOrder = battingOrder;
    }
}
