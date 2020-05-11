package kr.codesquad.baseball.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import lombok.*;

import java.util.List;

import static kr.codesquad.baseball.commonconstant.Judgement.*;

@Getter @Setter
@AllArgsConstructor
@Builder
public class StatusBoard {

    @JsonIgnore
    private Integer teamId;

    @JsonIgnore
    private Integer batterId;

    @JsonIgnore
    private Integer pitcherId;

    private String judgement;

    private int inning;

    @JsonIgnore
    private boolean firsthalf;

    private int score;

    private int strike;

    private int ball;

    private int hit;

    private int out;

    @JsonIgnore
    private int currentBattingOrder;

    @JsonIgnore
    private int addedHitCount;

    @JsonIgnore
    private List<BatterSummary> onBases;

    public StatusBoard(String judgement, int inning, boolean firsthalf, int strike, int ball, int hit, int out) {
        this.judgement = judgement;
        this.inning = inning;
        this.firsthalf = firsthalf;
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
            this.judgement = OUT;
        }

        if (currentBattingOrder > 9) currentBattingOrder = 1;
    }
}
