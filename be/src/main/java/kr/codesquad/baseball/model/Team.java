package kr.codesquad.baseball.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter @Setter
@Builder
public class Team {

    @Id
    @JsonProperty("teamId")
    private Integer id;

    @JsonProperty("teamName")
    private String name;

    @JsonIgnore
    private int score;

    public Team(Integer id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }
}
