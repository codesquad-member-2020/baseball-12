package kr.codesquad.baseball.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
@AllArgsConstructor
@Builder
public class TeamRecord {

    @Id
    Integer id;

    Integer gameId;

    Integer teamId;

    int inning;

    int score;

    int currentBattingOrder;

    Integer firstBaseman;

    Integer secondBaseman;

    Integer thirdBaseman;
}
