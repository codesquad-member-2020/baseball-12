package kr.codesquad.baseball.model;

import kr.codesquad.baseball.commonconstant.Judgement;
import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
public class StatusBoard {

    private Integer teamId;

    private String judgement;

    private int inning;

    private int score;

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

    public String createJudgement(double battingAverage) {
        double seed = Math.random();
        if (seed < 0.1) judgement = Judgement.OUT;
        else if (seed < (1 - battingAverage) / 2 - 0.05) judgement = Judgement.STRIKE;
        else if (seed < ((1 - battingAverage) / 2 - 0.05) * 2) judgement = Judgement.BALL;
        else judgement = Judgement.HIT;
        return judgement;
    }
}
