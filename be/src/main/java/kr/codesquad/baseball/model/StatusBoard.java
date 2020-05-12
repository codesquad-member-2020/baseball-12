package kr.codesquad.baseball.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class StatusBoard {

    private String judgement;

    private int inning;

    private int strike;

    private int ball;

    private int out;
}
