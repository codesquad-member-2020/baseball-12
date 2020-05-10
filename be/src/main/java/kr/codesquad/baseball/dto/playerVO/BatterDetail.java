package kr.codesquad.baseball.dto.playerVO;

import kr.codesquad.baseball.model.BattingRecord;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BatterDetail extends BatterSummary {

    private List<BattingRecord> battings;

    @Builder(builderMethodName = "batterDetailBuilder")
    public BatterDetail(int playerId, String playerName, int battingOrder, List<BattingRecord> battings) {
        super(playerId, playerName, battingOrder);
        this.battings = battings;
    }
}
