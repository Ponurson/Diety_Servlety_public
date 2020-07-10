package com.ponury.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ZestawUserDAO {


    private static final String CREATE_ZESTAW_QUERY =
            "INSERT INTO zestawy_users(data, category, grupa, user) VALUES (?, ?, ?, ?)";
    private static final String READ_ZESTAW_QUERY =
            "SELECT * FROM zestawy_users where id = ?";
    private static final String READ_ZESTAW_QUERY_BY_DATE_AND_USER =
            "SELECT * FROM zestawy_users where data = ? AND user = ?";
    private static final String READ_ZESTAW_QUERY_BY_DATE_AND_CATEGORY_AND_USER =
            "SELECT * FROM zestawy_users where data = ? AND category = ? AND user = ?";
    private static final String UPDATE_ZESTAW_QUERY =
            "UPDATE zestawy_users SET data = ?, category = ?, grupa = ?, user = ? where id = ?";
    private static final String DELETE_ZESTAW_QUERY =
            "DELETE FROM zestawy_users WHERE id = ?";
    private static final String FIND_ALL_ZESTAW_BY_USER_QUERY =
            "SELECT * FROM zestawy_users WHERE user = ?";
    private static final String FIND_ALL_ZESTAW_QUERY_BY_DATE_AND_CATEGORY_USER =
            "SELECT * FROM zestawy_users WHERE data >= ? AND category = ? AND user = ? ORDER BY data";
    private static String[] categories = {"Śniadanie", "II Śniadanie", "Lunch", "Obiad"};

    public static void delete(int zestawId) {
        try (Connection conn = DBUtils.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_ZESTAW_QUERY);
            statement.setInt(1, zestawId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void update(ZestawUser zestaw) {
        if (zestaw.getId() > 0) {
            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ZESTAW_QUERY);
                preparedStatement.setString(1, zestaw.getData());
                preparedStatement.setString(2, zestaw.getCategory());
                preparedStatement.setInt(3, zestaw.getGrupa());
                preparedStatement.setString(4, zestaw.getUser());
                preparedStatement.setInt(5, zestaw.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ZestawUser read(int id) {
        ZestawUser zestaw = null;
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

    public static ZestawUser readByDate(String data, String user) {
        ZestawUser zestaw = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ZESTAW_QUERY_BY_DATE_AND_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, data);
            preparedStatement.setString(2, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                zestaw = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zestaw;
    }

    public static ZestawUser readByDateAndCategory(String data, String category, String user) {
        ZestawUser zestaw = null;
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ZESTAW_QUERY_BY_DATE_AND_CATEGORY_AND_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, data);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                zestaw = mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zestaw;
    }

    public static List<ZestawUser> findAllByDateAndCategory(String data, String category, String user) {
        List<ZestawUser> zestawList = new ArrayList<ZestawUser>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ZESTAW_QUERY_BY_DATE_AND_CATEGORY_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, data);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                zestawList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zestawList;
    }

    public static List<ZestawUser> findAll(String user) {
        List<ZestawUser> zestawList = new ArrayList<>();
        try (Connection connection = DBUtils.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ZESTAW_BY_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                zestawList.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zestawList;
    }

    public static ZestawUser create(ZestawUser zestaw) {
        int result = 0;
        if (zestaw != null) {

            try (Connection connection = DBUtils.connect()) {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ZESTAW_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, zestaw.getData());
                preparedStatement.setString(2, zestaw.getCategory());
                preparedStatement.setInt(3, zestaw.getGrupa());
                preparedStatement.setString(4, zestaw.getUser());
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

    private static ZestawUser mapUser(ResultSet resultSet) throws SQLException {
        ZestawUser zestaw = new ZestawUser(resultSet.getString("data"),
                resultSet.getString("category"),
                resultSet.getInt("grupa"),
                resultSet.getString("user"));
        zestaw.setId(resultSet.getInt("id"));
        return zestaw;
    }

    public static void fillZestawy(String user, int ileDniPodRzad) {
        List<Danie> daniaList = DanieDAO.findAll();
        HashSet<Integer> grupy = new HashSet<>();
        for (Danie d : daniaList) {
            if (d.getGrupa() != 0 && d.getGrupa() != 1) {
                grupy.add(d.getGrupa());
            }
        }
        List<Integer> grupyList = new ArrayList<>(grupy);
        Collections.shuffle(grupyList);
        for (String category : categories) {
            int plusDays = 0;
            for (int grupa : grupyList) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDateTime dateForMeals;
                for (int i = 0; i < ileDniPodRzad; i++) {
                    dateForMeals = LocalDateTime.now();
                    dateForMeals = dateForMeals.plusDays(plusDays);
                    ZestawUser zestaw = new ZestawUser(sdf.format(dateForMeals), category, grupa, user);
                    ZestawUserDAO.create(zestaw);
                    plusDays++;
                }

            }
        }
    }

}
