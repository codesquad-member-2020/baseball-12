package kr.codesquad.baseball.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
@AllArgsConstructor
public class User {

    @Id
    private Integer id;

    private String name;

    private String email;
}
