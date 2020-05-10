package kr.codesquad.baseball.model;

import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
public class StatusBoard {

    private String judgement;

    private int inning;

    private int strike;

    private int ball;

    private int hit;

    private int out;

    private int currentBattingOrder;

    private List<BatterSummary> onBases;

    public StatusBoard(String judgement,int inning, int strike, int ball, int hit, int out) {
        this.judgement = judgement;
        this.inning = inning;
        this.strike = strike;
        this.ball = ball;
        this.hit = hit;
        this.out = out;
    }
}
