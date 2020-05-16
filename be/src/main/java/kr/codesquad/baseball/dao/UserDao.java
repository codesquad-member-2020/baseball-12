package kr.codesquad.baseball.dao;

import kr.codesquad.baseball.model.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User findAwayUserByGameId(int gameId) {
        String SQL = "SELECT u.id as userId, u.name as userName, u.email as userEmail " +
                     "FROM user u INNER JOIN game g ON u.id = g.away_user " +
                     "WHERE g.id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(SQL, new Object[]{gameId},
                (rs, rowNum) -> new User(rs.getInt("userId"),
                                         rs.getString("userName"),
                                         rs.getString("userEmail"))));
    }

    public User findHomeUserByGameId(int gameId) {
        String SQL = "SELECT u.id as userId, u.name as userName, u.email as userEmail " +
                     "FROM user u INNER JOIN game g ON u.id = g.home_user " +
                     "WHERE g.id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(SQL, new Object[]{gameId},
                (rs, rowNum) -> new User(rs.getInt("userId"),
                                         rs.getString("userName"),
                                         rs.getString("userEmail"))));
    }

    public User findUserById(int userId) {
        String SQL = "SELECT id, name, email FROM user " +
                     "WHERE id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(SQL, new Object[]{userId},
                                            (rs, rowNum) -> new User(rs.getInt("id"),
                                                                     rs.getString("name"),
                                                                     rs.getString("email"))));
    }
}
