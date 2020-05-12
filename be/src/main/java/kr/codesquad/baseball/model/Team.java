package kr.codesquad.baseball.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Team {

    @Id
    @JsonProperty("teamId")
    private Integer id;

    private String name;

    @JsonIgnore
    private int score;
}
