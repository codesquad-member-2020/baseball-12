package kr.codesquad.baseball.dto.playerVO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Batter extends PlayerVO {

    private int battingOrder;

    private double battingAverage;

    private int plateAppearance;

    private int hitCount;

    @Builder
    public Batter(int playerId, String playerName, int battingOrder, double battingAverage, int plateAppearance, int hitCount) {
        super(playerId, playerName);
        this.battingOrder = battingOrder;
        this.battingAverage = battingAverage;
        this.plateAppearance = plateAppearance;
        this.hitCount = hitCount;
    }
}
