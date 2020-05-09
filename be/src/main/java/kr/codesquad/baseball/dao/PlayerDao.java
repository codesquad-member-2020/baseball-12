package kr.codesquad.baseball.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PlayerDao {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PlayerDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Integer> findPlayerIdsByTeamId(int teamId) {
        String sql = "SELECT id FROM player WHERE team = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{teamId}, Integer.class);
    }

    public void insertPlayerRecord(int gameId, int playerId) {
        String sql = "INSERT INTO player_record (game, player) " +
                     "VALUES (:game, :player)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                            .addValue("game", gameId)
                                            .addValue("player", playerId);
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }
}
