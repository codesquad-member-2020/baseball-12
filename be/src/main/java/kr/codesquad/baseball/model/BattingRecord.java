package kr.codesquad.baseball.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
@AllArgsConstructor
@Builder
public class BattingRecord {

    @Id
    private Integer id;

    private Integer game;

    private Integer player;

    private int inning;

    private String judgement;

    private int strike;

    private int ball;

    private int out;

    public BattingRecord(String judgement, int strike, int ball) {
        this.judgement = judgement;
        this.strike = strike;
        this.ball = ball;
    }
}
