package kr.codesquad.baseball.dao;

import kr.codesquad.baseball.dto.playerVO.BatterSummary;
import kr.codesquad.baseball.dto.playerVO.Batter;
import kr.codesquad.baseball.dto.playerVO.Pitcher;
import kr.codesquad.baseball.model.BattingRecord;
import kr.codesquad.baseball.model.Game;
import kr.codesquad.baseball.model.StatusBoard;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
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
        String SQL = "SELECT id FROM player WHERE team = ?";
        return jdbcTemplate.queryForList(SQL, new Object[]{teamId}, Integer.class);
    }

    public List<Integer> findRecordedPlayerIds(int gameId, int teamId, int currentInning) {
        String SQL = "SELECT DISTINCT p.id FROM player p INNER JOIN batting_record br ON p.id = br.player " +
                     "INNER JOIN player_record pr ON p.id = pr.player " +
                     "WHERE br.game = ? AND p.team = ? AND br.inning = ? AND pr.plate_appearance > 0";
        return jdbcTemplate.queryForList(SQL, new Object[]{gameId, teamId, currentInning}, Integer.class);
    }

    public void insertPlayerRecord(int gameId, int playerId) {
        String SQL = "INSERT INTO player_record (game, player) " +
                     "VALUES (:game, :player)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                            .addValue("game", gameId)
                                            .addValue("player", playerId);
        namedParameterJdbcTemplate.update(SQL, namedParameters);
    }

    public BatterSummary findBatterSummaryById(int playerId) throws EmptyResultDataAccessException {
        String SQL = "SELECT id, name, batting_order FROM player WHERE id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(SQL, new Object[]{playerId},
                (rs, rowNum) -> BatterSummary.builder()
                                             .playerId(rs.getInt("id"))
                                             .playerName(rs.getString("name"))
                                             .battingOrder(rs.getInt("batting_order"))
                                             .build()));
    }

    public Batter findBatterPlayerByTeamIdWithOrder(int gameId, int teamId, int currentBattingOrder) {
        String SQL = "SELECT p.id as playerId, p.name as playerName, p.batting_order as battingOrder, " +
                            "p.batting_average as battingAverage, pr.plate_appearance as plateAppearance, pr.hit_count as hitCount " +
                     "FROM player p LEFT OUTER JOIN player_record pr ON p.id = pr.player " +
                     "WHERE pr.game = ? AND p.team = ? AND p.batting_order = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{gameId, teamId, currentBattingOrder},
                (rs, rowNum) -> Batter.builder()
                                      .playerId(rs.getInt("playerId"))
                                      .playerName(rs.getString("playerName"))
                                      .battingOrder(rs.getInt("battingOrder"))
                                      .battingAverage(rs.getDouble("battingAverage"))
                                      .plateAppearance(rs.getInt("plateAppearance"))
                                      .hitCount(rs.getInt("hitCount"))
                                      .build());
    }

    public List<BattingRecord> findBattingRecordsOfInningByIds(int gameId, int playerId, int currentInning) {
        String SQL = "SELECT br.id as id, br.game as gameId, br.player as playerId, br.inning as inning, br.judgement as judgement, " +
                            "br.strike_count as strikeCount, br.ball_count as ballCount, br.out_count as outCount " +
                     "FROM batting_record br INNER JOIN player p ON br.player = p.id " +
                     "WHERE br.game = ? AND br.player = ? AND br.inning = ?";
        return jdbcTemplate.query(SQL, new Object[]{gameId, playerId, currentInning},
                (rs, rowNum) -> BattingRecord.builder()
                                             .id(rs.getInt("id"))
                                             .game(rs.getInt("gameId"))
                                             .player(rs.getInt("playerId"))
                                             .inning(rs.getInt("inning"))
                                             .judgement(rs.getString("judgement"))
                                             .strike(rs.getInt("strikeCount"))
                                             .ball(rs.getInt("ballCount"))
                                             .out(rs.getInt("outCount"))
                                             .build());
    }

    public Pitcher findPitcherByIds(int gameId, int teamId) {
        String SQL = "SELECT p.id as playerId, p.name as playerName, pr.pitching_count as pitchingCount " +
                     "FROM player p INNER JOIN player_record pr ON p.id = pr.player " +
                     "WHERE pr.game = ? AND p.team = ? AND p.position = 'pitcher'";
        return DataAccessUtils.singleResult(jdbcTemplate.query(SQL, new Object[]{gameId, teamId},
                (rs, rowNum) -> Pitcher.builder()
                                       .playerId(rs.getInt("playerId"))
                                       .playerName(rs.getString("playerName"))
                                       .pitchingCount(rs.getInt("pitchingCount"))
                                       .build()));
    }

    public StatusBoard findRecentPlayerRecordOfInningByGameId(int gameId, int inning) {
        String SQL = "SELECT judgement, strike_count, ball_count, hit_count, out_count FROM batting_record " +
                     "WHERE game = ? AND inning = ? " +
                     "ORDER BY id DESC LIMIT 1";
        return DataAccessUtils.singleResult(jdbcTemplate.query(SQL, new Object[]{gameId, inning},
                (rs, rowNum) -> StatusBoard.builder()
                                           .inning(inning)
                                           .judgement(rs.getString("judgement"))
                                           .strike(rs.getInt("strike_count"))
                                           .ball(rs.getInt("ball_count"))
                                           .hit(rs.getInt("hit_count"))
                                           .out(rs.getInt("out_count"))
                                           .build()));
    }

    public void updatePlayerRecordOfBatter(int batterId, int hitCount, int gameId) {
        String SQL = "UPDATE player_record SET hit_count = hit_count + ? " +
                     "WHERE game = ? AND player = ?";
        jdbcTemplate.update(SQL, hitCount, gameId, batterId);
    }

    public void updatePlayerRecordOfPitcher(int pitcherId, int gameId) {
        String SQL = "UPDATE player_record SET pitching_count = pitching_count + 1 " +
                     "WHERE game = ? AND player = ?";
        jdbcTemplate.update(SQL, gameId, pitcherId);
    }

    public void insertBattingRecord(int batterId, int inning, String judgement, int strike, int ball, int hit, int out, int gameId) {
        String SQL = "INSERT INTO batting_record (game, player, inning, judgement, strike_count, ball_count, hit_count, out_count) " +
                     "VALUES (:gameId, :playerId, :inning, :judgement, :strike, :ball, :hit, :out)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                                .addValue("gameId", gameId)
                                                .addValue("playerId", batterId)
                                                .addValue("inning", inning)
                                                .addValue("judgement", judgement)
                                                .addValue("strike", strike)
                                                .addValue("ball", ball)
                                                .addValue("hit", hit)
                                                .addValue("out", out);
        namedParameterJdbcTemplate.update(SQL, namedParameters);
    }

    public Double findBattingAverage(int teamId, int currentBattingOrder) {
        String SQL = "SELECT batting_average FROM player WHERE team = ? AND batting_order = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{teamId, currentBattingOrder}, Double.class);
    }

    public Batter findBatterPlayerByIds(int gameId, int teamId, int playerId) {
        String SQL = "SELECT p.id as playerId, p.name as playerName, p.batting_order as battingOrder, p.batting_average as battingAverage, " +
                            "pr.hit_count as hitCount, pr.plate_appearance as plateAppearance " +
                     "FROM player_record pr INNER JOIN player p ON pr.player = p.id " +
                     "WHERE pr.game = ? AND p.team = ? AND pr.player = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{gameId, teamId, playerId},
                (rs, rowNum) -> Batter.builder()
                                      .playerId(rs.getInt("playerId"))
                                      .playerName(rs.getString("playerName"))
                                      .battingOrder(rs.getInt("battingOrder"))
                                      .battingAverage(rs.getDouble("battingAverage"))
                                      .hitCount(rs.getInt("hitCount"))
                                      .plateAppearance(rs.getInt("plateAppearance"))
                                      .build());
    }

    public Integer findTotalOutCountOfPlayerByIds(int gameId, int playerId, String judgement) {
        String SQL = "SELECT COUNT(judgement) FROM batting_record " +
                     "WHERE game = ? AND player = ? AND judgement = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{gameId, playerId, judgement}, Integer.class);
    }

    public void plusOnePointOfPlateAppearance(int gameId, int batterId) {
        String SQL = "UPDATE player_record SET plate_appearance = plate_appearance + 1 " +
                     "WHERE game = ? AND player = ?";
        jdbcTemplate.update(SQL, gameId, batterId);
    }

    public Integer findBatterIdByTeamIdWithOrder(int awayTeamId) {
        String SQL = "SELECT id FROM player " +
                     "WHERE team = ? AND batting_order = 1";
        return jdbcTemplate.queryForObject(SQL, new Object[]{awayTeamId}, Integer.class);
    }

    public Integer findBatterId(int teamId, int currentBattingOrder) {
        String SQL = "SELECT id FROM player " +
                     "WHERE team = ? AND batting_order = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{teamId, currentBattingOrder}, Integer.class);
    }

    public void updatePlateAppearanceOfNextBatter(int gameId, int nextBatterId, int plateAppearance) {
        String SQL = "UPDATE player_record SET plate_appearance = plate_appearance + ? " +
                     "WHERE game = ? AND player = ?";
        jdbcTemplate.update(SQL, plateAppearance, gameId, nextBatterId);
    }
}
