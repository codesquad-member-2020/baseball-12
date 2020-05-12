package kr.codesquad.baseball.dao;

import kr.codesquad.baseball.model.Game;
import kr.codesquad.baseball.model.StatusBoard;
import kr.codesquad.baseball.model.TeamRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TeamDao {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TeamDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void insertTeamRecordOfInning(int gameId, int teamId, int inning) {
        String SQL = "INSERT INTO team_record (game, team, inning) " +
                     "VALUES (:game, :team, :inning)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                                .addValue("game", gameId)
                                                .addValue("team", teamId)
                                                .addValue("inning", inning);
        namedParameterJdbcTemplate.update(SQL, namedParameters);
    }

    public String findTeamNameById(int teamId) {
        String SQL = "SELECT name FROM team WHERE id = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{teamId}, String.class);
    }

    public TeamRecord findTeamRecordByIds(int gameId, int teamId, int inning) {
        String SQL = "SELECT id, game, team, inning, score, current_batting_order, first_baseman, second_baseman, third_baseman " +
                     "FROM team_record WHERE game = ? AND team = ? AND inning = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{gameId, teamId, inning},
                (rs, rowNum) -> TeamRecord.builder()
                                          .id(rs.getInt("id"))
                                          .gameId(rs.getInt("game"))
                                          .teamId(rs.getInt("team"))
                                          .inning(rs.getInt("inning"))
                                          .score(rs.getInt("score"))
                                          .currentBattingOrder(rs.getInt("current_batting_order"))
                                          .firstBaseman(rs.getInt("first_baseman"))
                                          .secondBaseman(rs.getInt("second_baseman"))
                                          .thirdBaseman(rs.getInt("third_baseman"))
                                          .build());
    }

    public void updateTeamRecordOfCurrentInning(StatusBoard statusBoard, Game game) {
        String SQL = "UPDATE team_record SET score = :score, current_batting_order = :currentBattingOrder " +
                     "WHERE team = :teamId AND game = :gameId AND inning = :inning";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                            .addValue("score", statusBoard.getScore())
                                            .addValue("currentBattingOrder", statusBoard.getCurrentBattingOrder())
                                            .addValue("teamId", statusBoard.getTeamId())
                                            .addValue("gameId", game.getId())
                                            .addValue("inning", statusBoard.getInning());
        namedParameterJdbcTemplate.update(SQL, namedParameters);
    }

    public Integer findCurrentBattingOrderOfInning(int gameId, int teamId, int inning) {
        String SQL = "SELECT current_batting_order FROM team_record " +
                     "WHERE game = ? AND team = ? AND inning = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{gameId, teamId, inning}, Integer.class);
    }

    public void updateCurrentGameInformation(int inning, boolean firsthalf, int gameId) {
        String SQL = "UPDATE game SET inning = ?, is_firsthalf = ? " +
                     "WHERE id = ?";
        jdbcTemplate.update(SQL, inning, firsthalf, gameId);
    }

    public void updateCurrentBattingOrderOfInning(int gameId, int teamId, int inning, int currentBattingOrder) {
        String SQL = "UPDATE team_record SET current_batting_order = ? " +
                     "WHERE game = ? AND team = ? AND inning = ?";
        jdbcTemplate.update(SQL, new Object[]{currentBattingOrder, gameId, teamId, inning});
    }

    public Integer findTotalScoreOfTeam(int gameId, int teamId) {
        String SQL = "SELECT SUM(score) as totalScore FROM team_record " +
                "WHERE game = ? AND team = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{gameId, teamId}, Integer.class);
    }
}
