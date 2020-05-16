package kr.codesquad.baseball.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
@Builder
public class Game {

    @Id
    @JsonProperty("matchId")
    private Integer id;

    private Integer awayTeam;

    private Integer homeTeam;

    private Integer awayUser;

    private Integer homeUser;

    @JsonIgnore
    private int inning;

    @JsonIgnore
    private boolean isFirsthalf;
}
