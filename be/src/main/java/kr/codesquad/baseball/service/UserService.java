package kr.codesquad.baseball.service;

import kr.codesquad.baseball.dao.UserDao;
import kr.codesquad.baseball.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findAwayUserByGameId(int gameId) {
        return userDao.findAwayUserByGameId(gameId);
    }

    public User findHomeUserByGameId(int gameId) {
        return userDao.findHomeUserByGameId(gameId);
    }

    public User findUserById(int userId) {
        return userDao.findUserById(userId);
    }
}
