package com.ponury.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaniaWKoszykuDAO {


    private static final String CREATE_DANIA_W_KOSZYKU_QUERY =
            "INSERT INTO dania_w_koszyku(id_zestawu, user) VALUES (?, ?)";
    private static final String READ_DANIA_W_KOSZYKU_QUERY =
            "SELECT * FROM dania_w_koszyku where id = ?";
    private static final String UPDATE_DANIA_W_KOSZYKU_QUERY =
            "UPDATE dania_w_koszyku SET id_zestawu = ?, user = ? where id = ?";
    private static final String DELETE_DANIA_W_KOSZYKU_QUERY =
            "DELETE FROM dania_w_koszyku WHERE id = ?";
    private static final String FIND_ALL_DANIA_W_KOSZYKU_BY_USER_QUERY =
            "SELECT * FROM dania_w_koszyku WHERE user = ?";
    private static final String DELETE_ALL_DANIA_W_KOSZYKU_QUERY =
            "DELETE FROM dania_w_koszyku WHERE user = ?";

    public static void delete(int danieId) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_DANIA_W_KOSZYKU_QUERY);
            statement.setInt(1, danieId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll(String user) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_ALL_DANIA_W_KOSZYKU_QUERY);
            statement.setString(1, user);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void update(DaniaWKoszyku daniaWKoszyku) {
        if (daniaWKoszyku.getId() > 0) {
            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DANIA_W_KOSZYKU_QUERY);
                preparedStatement.setInt(1, daniaWKoszyku.getIdZestawu());
                preparedStatement.setString(2, daniaWKoszyku.getUser());
                preparedStatement.setInt(3, daniaWKoszyku.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static DaniaWKoszyku read(int id) {
        DaniaWKoszyku daniaWKoszyku = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_DANIA_W_KOSZYKU_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                daniaWKoszyku = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daniaWKoszyku;
    }

    public static List<DaniaWKoszyku> findAllByUser(String user) {
        List<DaniaWKoszyku> daniaWKoszykus = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DANIA_W_KOSZYKU_BY_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                daniaWKoszykus.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daniaWKoszykus;
    }

    public static DaniaWKoszyku create(DaniaWKoszyku daniaWKoszyku) {
        int result = 0;
        if (daniaWKoszyku != null) {

            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DANIA_W_KOSZYKU_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, daniaWKoszyku.getIdZestawu());
                preparedStatement.setString(2, daniaWKoszyku.getUser());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    daniaWKoszyku.setId((int) rs.getLong(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return daniaWKoszyku;
    }

    private static DaniaWKoszyku mapUser(ResultSet resultSet) throws SQLException {
        DaniaWKoszyku daniaWKoszyku = new DaniaWKoszyku(resultSet.getInt("id_zestawu"),
                resultSet.getString("user"));
        daniaWKoszyku.setId(resultSet.getInt("id"));
        return daniaWKoszyku;
    }


}
