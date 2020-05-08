package kr.codesquad.baseball.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Player {

    @Id
    private Integer id;

    private String name;

    private String position;

    private int battingOrder;

    private double battingAverage;
}
