package com.ponury.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SettingDAO {

    private static final String CREATE_SETTING_QUERY =
            "INSERT INTO settings(name, amount, user) VALUES (?, ?, ?)";
    private static final String READ_SETTING_QUERY =
            "SELECT * FROM settings where id = ?";
    private static final String UPDATE_SETTING_QUERY =
            "UPDATE settings SET name = ?, amount = ?, user = ? where id = ?";
    private static final String DELETE_SETTING_QUERY =
            "DELETE FROM settings WHERE id = ?";
    private static final String FIND_ALL_SETTINGS_QUERY =
            "SELECT * FROM settings";
    private static final String FIND_ALL_SETTINGS_BY_USER_QUERY =
            "SELECT * FROM settings WHERE user = ?";

    public static void delete(int settingId) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_SETTING_QUERY);
            statement.setInt(1, settingId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void update(Setting setting) {
        if (setting.getId() > 0) {
            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SETTING_QUERY);
                preparedStatement.setString(1, setting.getName());
                preparedStatement.setString(2, setting.getValue());
                preparedStatement.setString(3, setting.getUser());
                preparedStatement.setInt(4, setting.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Setting read(int id) {
        Setting setting = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_SETTING_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                setting = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return setting;
    }

    public static List<Setting> findAll() {
        List<Setting> settingList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SETTINGS_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                settingList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settingList;
    }

    public static List<Setting> findAllByUser(String user) {
        List<Setting> settingList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SETTINGS_BY_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                settingList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settingList;
    }

    public static Setting create(Setting setting) {
        int result = 0;
        if (setting != null) {

            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SETTING_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, setting.getName());
                preparedStatement.setString(2, setting.getValue());
                preparedStatement.setString(3, setting.getUser());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    setting.setId((int) rs.getLong(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return setting;
    }

    private static Setting mapUser(ResultSet resultSet) throws SQLException {
        Setting setting = new Setting(resultSet.getString("name"),
                resultSet.getString("amount"),
                resultSet.getString("user"));
        setting.setId(resultSet.getInt("id"));
        return setting;
    }

}


