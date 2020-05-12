package kr.codesquad.baseball.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BattingRecord {

    private String judgement;

    private int strike;

    private int ball;

    public BattingRecord(String judgement, int strike, int ball) {
        this.judgement = judgement;
        this.strike = strike;
        this.ball = ball;
    }
}
