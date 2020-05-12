package kr.codesquad.baseball.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
@Builder
public class Game {

    @Id
    private Integer id;

    private Integer awayTeam;

    private Integer homeTeam;

    private Integer awayUser;

    private Integer homeUser;

    private int inning;

    private boolean isFirsthalf;
}
