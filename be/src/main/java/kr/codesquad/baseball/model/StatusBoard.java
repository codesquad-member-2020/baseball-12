package kr.codesquad.baseball.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import lombok.*;

import java.util.List;

import static kr.codesquad.baseball.commonconstant.Judgement.*;
import static kr.codesquad.baseball.commonconstant.ConstatnsCoveringMagicNumber.*;

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

    private boolean firsthalf;

    @JsonIgnore
    private int score;

    private int strike;

    private int ball;

    @JsonIgnore
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
        double seedOfJudgement = Math.random();
        final boolean FORMULA_GENERATING_JUDGEMENT_OF_OUT = seedOfJudgement < 0.1;
        final boolean FORMULA_GENERATING_JUDGEMENT_OF_STRIKE = seedOfJudgement < (1 - battingAverage) / 2 - 0.05;
        final boolean FORMULA_GENERATING_JUDGEMENT_OF_BALL = seedOfJudgement < ((1 - battingAverage) / 2 - 0.05) * 2;

        if (FORMULA_GENERATING_JUDGEMENT_OF_OUT) { judgement = OUT; }
        else if (FORMULA_GENERATING_JUDGEMENT_OF_STRIKE) { judgement = STRIKE; }
        else if (FORMULA_GENERATING_JUDGEMENT_OF_BALL) { judgement = BALL; }
        else { judgement = HIT; }
        return judgement;
    }

    public void updateStatus(String judgement) {
        switch (judgement) {
        case STRIKE:
            strike += ONE_POINT;
            arrangeSideEffectOfStatus(STRIKE);
            break;
        case BALL:
            ball += ONE_POINT;
            arrangeSideEffectOfStatus(BALL);
            break;
        case HIT:
            strike = INITIAL_VALUE;
            ball = INITIAL_VALUE;
            hit += ONE_POINT;
            addedHitCount += ONE_POINT;
            currentBattingOrder += ONE_POINT;
            arrangeSideEffectOfStatus(HIT);
            break;
        case OUT:
            strike = INITIAL_VALUE;
            ball = INITIAL_VALUE;
            out += ONE_POINT;
            currentBattingOrder += ONE_POINT;
            arrangeSideEffectOfStatus(OUT);
            break;
        }
    }

    public void arrangeSideEffectOfStatus(String judgement) {
        if (ball == FOUR_BALL) {
            ball = INITIAL_VALUE;
            hit += ONE_POINT;
            addedHitCount += ONE_POINT;
            currentBattingOrder += ONE_POINT;
            judgement = HIT;
            this.judgement = HIT;
        }

        if (judgement.equals(HIT) && hit > THREE_HIT) score += ONE_POINT;

        if (strike == THREE_STRIKE) {
            strike = INITIAL_VALUE;
            ball = INITIAL_VALUE;
            out += ONE_POINT;
            currentBattingOrder += ONE_POINT;
            this.judgement = OUT;
        }

        if (currentBattingOrder > LAST_BATTING_ORDER) currentBattingOrder = FIRST_BATTING_ORDER;
    }
}
