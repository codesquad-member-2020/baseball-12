package kr.codesquad.baseball.model;

import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import lombok.*;

import java.util.List;

import static kr.codesquad.baseball.commonconstant.Judgement.*;

@Getter @Setter
@AllArgsConstructor
@Builder
public class StatusBoard {

    private Integer teamId;

    private Integer batterId;

    private Integer pitcherId;

    private String judgement;

    private int inning;

    private boolean isFirsthalf;

    private int score;

    private int strike;

    private int ball;

    private int hit;

    private int out;

    private int currentBattingOrder;

    private int addedHitCount;

    private List<BatterSummary> onBases;

    public StatusBoard(String judgement,int inning, boolean isFirsthalf, int strike, int ball, int hit, int out) {
        this.judgement = judgement;
        this.inning = inning;
        this.isFirsthalf = isFirsthalf;
        this.strike = strike;
        this.ball = ball;
        this.hit = hit;
        this.out = out;
    }

    public void executePitch(double battingAverage) {
        String judgement = createJudgement(battingAverage);
        updateStatus(judgement);
    }

    public String createJudgement(double battingAverage) {
        double seed = Math.random();
        if (seed < 0.1) judgement = OUT;
        else if (seed < (1 - battingAverage) / 2 - 0.05) judgement = STRIKE;
        else if (seed < ((1 - battingAverage) / 2 - 0.05) * 2) judgement = BALL;
        else judgement = HIT;
        return judgement;
    }

    public void updateStatus(String judgement) {
        switch (judgement) {
        case STRIKE:
            strike += 1;
            arrangeSideEffectOfStatus(STRIKE);
            break;
        case BALL:
            ball += 1;
            arrangeSideEffectOfStatus(BALL);
            break;
        case HIT:
            strike = 0;
            ball = 0;
            hit += 1;
            addedHitCount += 1;
            currentBattingOrder += 1;
            arrangeSideEffectOfStatus(HIT);
            break;
        case OUT:
            strike = 0;
            ball = 0;
            out += 1;
            currentBattingOrder += 1;
            arrangeSideEffectOfStatus(OUT);
            break;
        }
    }

    public void arrangeSideEffectOfStatus(String judgement) {
        if (ball == 4) {
            ball = 0;
            hit += 1;
            addedHitCount += 1;
            currentBattingOrder += 1;
            judgement = HIT;
        }

        if (judgement.equals(HIT) && hit > 3) score += 1;

        if (strike == 3) {
            strike = 0;
            ball = 0;
            out += 1;
            currentBattingOrder += 1;
        }

        if (currentBattingOrder > 9) currentBattingOrder = 1;
    }
}
