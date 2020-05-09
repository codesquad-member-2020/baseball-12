package kr.codesquad.baseball.dao;

import kr.codesquad.baseball.model.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class GameDao {

    private final JdbcTemplate jdbcTemplate;

    public GameDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Game findGameById(Integer gameId) {
        String sql = "SELECT id, away_team, home_team, away_user, home_user, inning, is_firsthalf " +
                     "FROM game WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{gameId},
                (rs, rowNum) -> Game.builder()
                                    .id(rs.getInt("id"))
                                    .awayTeam(rs.getInt("away_team"))
                                    .homeTeam(rs.getInt("home_team"))
                                    .awayUser(rs.getInt("away_user"))
                                    .homeUser(rs.getInt("home_user"))
                                    .inning(rs.getInt("inning"))
                                    .isFirsthalf(rs.getBoolean("is_firsthalf"))
                                    .build());
    }
}
