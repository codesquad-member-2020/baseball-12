package kr.codesquad.baseball.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private Integer game;

    @JsonIgnore
    private Integer player;

    @JsonIgnore
    private int inning;

    private String judgement;

    private int strike;

    private int ball;

    @JsonIgnore
    private int out;

    public BattingRecord(String judgement, int strike, int ball) {
        this.judgement = judgement;
        this.strike = strike;
        this.ball = ball;
    }
}
