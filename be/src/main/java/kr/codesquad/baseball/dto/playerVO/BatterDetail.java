package kr.codesquad.baseball.dto.playerVO;

import kr.codesquad.baseball.dto.BattingRecord;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BatterDetail extends Batter {

    private List<BattingRecord> battings;

    @Builder
    public BatterDetail(int playerId, String playerName, int battingOrder, int plateAppearance, int hitCount, List<BattingRecord> battings) {
        super(playerId, playerName, battingOrder, plateAppearance, hitCount);
        this.battings = battings;
    }
}
