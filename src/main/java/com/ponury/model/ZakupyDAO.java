package com.ponury.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZakupyDAO {


    private static final String CREATE_ZAKUPY_QUERY =
            "INSERT INTO lista_zakupow(ingredients, amount, bought, user ) VALUES (?, ?, ?, ?)";
    private static final String READ_ZAKUPY_QUERY =
            "SELECT * FROM lista_zakupow where id = ?";
    private static final String UPDATE_ZAKUPY_QUERY =
            "UPDATE lista_zakupow SET ingredients = ?, amount = ?, bought = ?, user = ? where id = ?";
    private static final String DELETE_ZAKUPY_QUERY =
            "DELETE FROM lista_zakupow WHERE id = ?";
    private static final String FIND_ALL_ZAKUPY_QUERY =
            "SELECT * FROM lista_zakupow";
    private static final String FIND_ALL_ZAKUPY_BY_USER_QUERY =
            "SELECT * FROM lista_zakupow WHERE user = ?";
    private static final String DELETE_ALL_ZAKUPY_QUERY =
            "DELETE FROM lista_zakupow WHERE user = ?";

    public static void deleteAll(String user) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_ALL_ZAKUPY_QUERY);
            statement.setString(1, user);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int zakupyId) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_ZAKUPY_QUERY);
            statement.setInt(1, zakupyId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void update(Zakupy zakupy) {
        if (zakupy.getId() > 0) {
            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ZAKUPY_QUERY);
                preparedStatement.setString(1, zakupy.getName());
                preparedStatement.setInt(2, zakupy.getAmount());
                preparedStatement.setBoolean(3, zakupy.isBought());
                preparedStatement.setString(4, zakupy.getUser());
                preparedStatement.setInt(5, zakupy.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Zakupy read(int id) {
        Zakupy zakupy = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ZAKUPY_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                zakupy = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zakupy;
    }

    public static List<Zakupy> findAll() {
        List<Zakupy> zakupyList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ZAKUPY_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                zakupyList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zakupyList;
    }

    public static List<Zakupy> findAllByUser(String user) {
        List<Zakupy> zakupyList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ZAKUPY_BY_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                zakupyList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zakupyList;
    }

    public static Zakupy create(Zakupy zakupy) {
        int result = 0;
        if (zakupy != null) {

            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ZAKUPY_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, zakupy.getName());
                preparedStatement.setInt(2, zakupy.getAmount());
                preparedStatement.setBoolean(3, zakupy.isBought());
                preparedStatement.setString(4, zakupy.getUser());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    zakupy.setId((int) rs.getLong(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return zakupy;
    }

    private static Zakupy mapUser(ResultSet resultSet) throws SQLException {
        Zakupy zakupy = new Zakupy(resultSet.getString("ingredients"),
                resultSet.getInt("amount"),
                resultSet.getBoolean("bought"),
                resultSet.getString("user"));
        zakupy.setId(resultSet.getInt("id"));
        return zakupy;
    }

}

