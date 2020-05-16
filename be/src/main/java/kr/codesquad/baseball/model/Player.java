package kr.codesquad.baseball.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter @Setter
@Builder
public class Player {

    @Id
    private Integer id;

    private String name;

    private String position;

    private int battingOrder;

    private double battingAverage;

    public Player(Integer id, String name, String position, int battingOrder, double battingAverage) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.battingOrder = battingOrder;
        this.battingAverage = battingAverage;
    }
}
