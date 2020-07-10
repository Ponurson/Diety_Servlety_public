package com.ponury.model;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, passwd) VALUES (?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, passwd = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";

    public static void delete(int userId) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void update(User user) {
        if (user.getId() > 0) {
            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, BCrypt.hashpw(user.getPasswd(), BCrypt.gensalt()));
                preparedStatement.setInt(3, user.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static User read(int id) {
        User user = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<User> findAll() {
        List<User> settingList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                settingList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settingList;
    }

    public static User create(User user) {
        int result = 0;
        if (user != null) {

            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, BCrypt.hashpw(user.getPasswd(), BCrypt.gensalt()));
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    user.setId((int) rs.getLong(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    private static User mapUser(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getString("username"),
                resultSet.getString("passwd"));
        user.setId(resultSet.getInt("id"));
        return user;
    }

    public static User autorization(String username, String password) {
        List<User> userList = UserDAO.findAll();
        User user = null;
        for (User a : userList) {
            if (a.getUsername().equals(username) && BCrypt.checkpw(password, a.getPasswd())) {
                user = UserDAO.read(a.getId());
            }
        }
        return user;
    }

}
