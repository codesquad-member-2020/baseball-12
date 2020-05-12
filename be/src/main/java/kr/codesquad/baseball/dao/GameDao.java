package kr.codesquad.baseball.dao;

import kr.codesquad.baseball.model.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class GameDao {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GameDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Game findMatchTypeById(Integer matchId) {
        String SQL = "SELECT id, away_team, home_team, away_user, home_user, inning, is_firsthalf " +
                     "FROM matches WHERE id = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{matchId},
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

    public Game findGameById(Integer gameId) {
        String SQL = "SELECT id, away_team, home_team, away_user, home_user, inning, is_firsthalf " +
                     "FROM game WHERE id = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{gameId},
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

    public Integer insertNewGame(Game matchType) {
        String SQL = "INSERT INTO game (away_team, home_Team, inning, is_firsthalf) " +
                     "VALUES (:awayTeam, :homeTeam, :inning, :isFirsthalf)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                            .addValue("awayTeam", matchType.getAwayTeam())
                                            .addValue("homeTeam", matchType.getHomeTeam())
                                            .addValue("inning", matchType.getInning())
                                            .addValue("isFirsthalf", matchType.isFirsthalf());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Integer generatedGameId = namedParameterJdbcTemplate.update(SQL, namedParameters, keyHolder, new String[]{"generatedGameId"});
        return keyHolder.getKey().intValue();
    }
}
