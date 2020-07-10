//package com.ponury.model;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class DBUtils {
//    private static DataSource dataSource;
//    public static Connection connect() throws SQLException {
//       return getInstance().getConnection();   }
//    private static DataSource getInstance() {
//        if (dataSource == null) {
//            try {
//                Context initContext = new InitialContext();
//                Context envContext = (Context)initContext.lookup("java:/comp/env");
//                dataSource = (DataSource)envContext.lookup("jdbc/diety_online");
//                System.out.println("connection was established");
//            } catch (NamingException e) { e.printStackTrace(); }
//        }
//        return dataSource;
//    }
//}

package com.ponury.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/diety_online?useSSL=false&characterEncoding=utf8";
    private final static String DB_USER = "";
    private final static String DB_PASS = "";


    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
