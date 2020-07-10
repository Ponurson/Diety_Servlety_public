package com.ponury.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrzelicznikDAO {

    private static final String CREATE_PRZELICZNIK_QUERY =
            "INSERT INTO przelicznik_jednostek(ingredients, unit, converter) VALUES (?, ?, ?)";
    private static final String READ_PRZELICZNIK_QUERY =
            "SELECT * FROM przelicznik_jednostek where id = ?";
    private static final String READ_PRZELICZNIK_QUERY_BY_INGREDIENT =
            "SELECT * FROM przelicznik_jednostek where ingredients = ?";
    private static final String UPDATE_PRZELICZNIK_QUERY =
            "UPDATE przelicznik_jednostek SET ingredients = ?, unit = ?, converter = ? where id = ?";
    private static final String DELETE_PRZELICZNIK_QUERY =
            "DELETE FROM przelicznik_jednostek WHERE id = ?";
    private static final String FIND_ALL_PRZELICZNIK_QUERY =
            "SELECT * FROM przelicznik_jednostek";

    public static void delete(int przlicznikId) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_PRZELICZNIK_QUERY);
            statement.setInt(1, przlicznikId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void update(Przelicznik przelicznik) {
        if (przelicznik.getId() > 0) {
            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRZELICZNIK_QUERY);
                preparedStatement.setString(1, przelicznik.getIngredient());
                preparedStatement.setString(2, przelicznik.getUnit());
                preparedStatement.setDouble(3, przelicznik.getConverter());
                preparedStatement.setInt(4, przelicznik.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Przelicznik read(int id) {
        Przelicznik przelicznik = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_PRZELICZNIK_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                przelicznik = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return przelicznik;
    }

    public static Przelicznik readByIngredient(String ingredient) {
        Przelicznik przelicznik = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_PRZELICZNIK_QUERY_BY_INGREDIENT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ingredient);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                przelicznik = (mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return przelicznik;
    }

    public static List<Przelicznik> findAll() {
        List<Przelicznik> przelicznikList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRZELICZNIK_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                przelicznikList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return przelicznikList;
    }

    public static Przelicznik create(Przelicznik przelicznik) {
        int result = 0;
        if (przelicznik != null) {

            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PRZELICZNIK_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, przelicznik.getIngredient());
                preparedStatement.setString(2, przelicznik.getUnit());
                preparedStatement.setDouble(3, przelicznik.getConverter());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    przelicznik.setId((int) rs.getLong(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return przelicznik;
    }

    private static Przelicznik mapUser(ResultSet resultSet) throws SQLException {
        Przelicznik przelicznik = new Przelicznik(resultSet.getString("ingredients"),
                resultSet.getString("unit"),
                resultSet.getDouble("converter"));
        przelicznik.setId(resultSet.getInt("id"));
        return przelicznik;
    }
}
