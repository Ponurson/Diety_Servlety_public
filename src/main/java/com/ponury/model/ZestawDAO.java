package com.ponury.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZestawDAO {


    private static final String CREATE_ZESTAW_QUERY =
            "INSERT INTO zestawy_na_kolejne_dni(data, category, grupa) VALUES (?, ?, ?)";
    private static final String READ_ZESTAW_QUERY =
            "SELECT * FROM zestawy_na_kolejne_dni where id = ?";
    private static final String READ_ZESTAW_QUERY_BY_DATE =
            "SELECT * FROM zestawy_na_kolejne_dni where data = ?";
    private static final String READ_ZESTAW_QUERY_BY_DATE_AND_CATEGORY =
            "SELECT * FROM zestawy_na_kolejne_dni where data = ? AND category = ?";
    private static final String UPDATE_ZESTAW_QUERY =
            "UPDATE zestawy_na_kolejne_dni SET data = ?, category = ?, grupa = ? where id = ?";
    private static final String DELETE_ZESTAW_QUERY =
            "DELETE FROM zestawy_na_kolejne_dni WHERE id = ?";
    private static final String FIND_ALL_ZESTAW_QUERY =
            "SELECT * FROM zestawy_na_kolejne_dni";
    private static final String FIND_ALL_ZESTAW_QUERY_BY_DATE_AND_CATEGORY =
            "SELECT * FROM zestawy_na_kolejne_dni WHERE data >= ? AND category = ? ORDER BY data";

    public static void delete(int zestawId) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_ZESTAW_QUERY);
            statement.setInt(1, zestawId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void update(Zestaw zestaw) {
        if (zestaw.getId() > 0) {
            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ZESTAW_QUERY);
                preparedStatement.setString(1, zestaw.getData());
                preparedStatement.setString(2, zestaw.getCategory());
                preparedStatement.setInt(3, zestaw.getGrupa());
                preparedStatement.setInt(4, zestaw.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Zestaw read(int id) {
        Zestaw zestaw = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ZESTAW_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                zestaw = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zestaw;
    }

    public static Zestaw readByDate(String data) {
        Zestaw zestaw = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ZESTAW_QUERY_BY_DATE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, data);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                zestaw = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zestaw;
    }

    public static Zestaw readByDateAndCategory(String data, String category) {
        Zestaw zestaw = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ZESTAW_QUERY_BY_DATE_AND_CATEGORY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, data);
            preparedStatement.setString(2, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                zestaw = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zestaw;
    }

    public static List<Zestaw> findAllByDateAndCategory(String data, String category) {
        List<Zestaw> zestawList = new ArrayList<Zestaw>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ZESTAW_QUERY_BY_DATE_AND_CATEGORY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, data);
            preparedStatement.setString(2, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                zestawList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zestawList;
    }

    public static List<Zestaw> findAll() {
        List<Zestaw> zestawList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ZESTAW_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                zestawList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zestawList;
    }

    public static Zestaw create(Zestaw zestaw) {
        int result = 0;
        if (zestaw != null) {

            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ZESTAW_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, zestaw.getData());
                preparedStatement.setString(2, zestaw.getCategory());
                preparedStatement.setInt(3, zestaw.getGrupa());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    zestaw.setId((int) rs.getLong(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return zestaw;
    }

    private static Zestaw mapUser(ResultSet resultSet) throws SQLException {
        Zestaw zestaw = new Zestaw(resultSet.getString("data"),
                resultSet.getString("category"),
                resultSet.getInt("grupa"));
        zestaw.setId(resultSet.getInt("id"));
        return zestaw;
    }

}
