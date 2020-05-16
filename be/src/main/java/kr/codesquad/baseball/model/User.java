package kr.codesquad.baseball.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
public class User {

    @Id
    @JsonProperty("userId")
    private Integer id;

    private String name;

    private String email;

    public User() {}

    public User(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
