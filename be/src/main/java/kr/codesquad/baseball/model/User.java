package kr.codesquad.baseball.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
@AllArgsConstructor
public class User {

    @Id
    @JsonProperty("userId")
    private Integer id;

    private String name;

    private String email;
}
