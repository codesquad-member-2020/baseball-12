package kr.codesquad.baseball.dao;

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
        String sql = "INSERT INTO team_record (game, team, inning) " +
                     "VALUES (:game, :team, :inning)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                                .addValue("game", gameId)
                                                .addValue("team", teamId)
                                                .addValue("inning", inning);
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }
}
