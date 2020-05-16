package kr.codesquad.baseball.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
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

    public TeamRecord(Integer id, Integer gameId, Integer teamId, int inning, int score, int currentBattingOrder, Integer firstBaseman, Integer secondBaseman, Integer thirdBaseman) {
        this.id = id;
        this.gameId = gameId;
        this.teamId = teamId;
        this.inning = inning;
        this.score = score;
        this.currentBattingOrder = currentBattingOrder;
        this.firstBaseman = firstBaseman;
        this.secondBaseman = secondBaseman;
        this.thirdBaseman = thirdBaseman;
    }
}
