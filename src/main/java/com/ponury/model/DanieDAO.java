package com.ponury.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DanieDAO {

    private static final String CREATE_DANIE_QUERY =
            "INSERT INTO dania(name, ingredients, recipe, category, grupa) VALUES (?, ?, ?, ?, ?)";
    private static final String READ_DANIE_QUERY =
            "SELECT * FROM dania where id = ?";
    private static final String READ_DANIE_QUERY_BY_GROUP_AND_CATEGORY =
            "SELECT * FROM dania where grupa = ? AND category = ?";
    private static final String UPDATE_DANIE_QUERY =
            "UPDATE dania SET name = ?, ingredients = ?, recipe = ?, category = ?, grupa = ? where id = ?";
    private static final String DELETE_DANIE_QUERY =
            "DELETE FROM dania WHERE id = ?";
    private static final String FIND_ALL_DANIA_QUERY =
            "SELECT * FROM dania";

    public static void delete(int solutionId) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_DANIE_QUERY);
            statement.setInt(1, solutionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void update(Danie danie) {
        if (danie.getId() > 0) {
            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DANIE_QUERY);
                preparedStatement.setString(1, danie.getName());
                preparedStatement.setString(2, danie.getIngredientsString());
                preparedStatement.setString(3, danie.getRecipe());
                preparedStatement.setString(4, danie.getCategory());
                preparedStatement.setInt(5, danie.getGrupa());
                preparedStatement.setInt(6, danie.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Danie read(int id) {
        Danie danie = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_DANIE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                danie = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danie;
    }

    public static List<Danie> readByGroupAndCategory(int grupa, String category) {
        List<Danie> danieList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_DANIE_QUERY_BY_GROUP_AND_CATEGORY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, grupa);
            preparedStatement.setString(2, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                danieList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danieList;
    }

    public static List<Danie> findAll() {
        List<Danie> danieList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DANIA_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                danieList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danieList;
    }

    public static Danie create(Danie danie) {
        int result = 0;
        if (danie != null) {

            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DANIE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, danie.getName());
                preparedStatement.setString(2, danie.getIngredientsString());
                preparedStatement.setString(3, danie.getRecipe());
                preparedStatement.setString(4, danie.getCategory());
                preparedStatement.setInt(5, danie.getGrupa());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    danie.setId((int) rs.getLong(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danie;
    }

    private static Danie mapUser(ResultSet resultSet) throws SQLException {
        Danie danie = new Danie(resultSet.getString("name"),
                resultSet.getString("recipe"),
                resultSet.getString("category"),
                resultSet.getInt("grupa"),
                resultSet.getString("ingredients"));
        danie.setId(resultSet.getInt("id"));
        return danie;
    }

}
