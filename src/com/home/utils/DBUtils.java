package com.home.utils;

import java.sql.*;

public class DBUtils {

    private static Connection connection;

    private static String url = "jdbc:mysql://47.106.133.35:3306/home_db?characterEncoding=utf-8";
    private static String user = "home_db";
    private static String pwd = "home_db";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {

        if(connection==null){
            connection = DriverManager.getConnection(url, user, pwd);
        }

        return connection;
    }

    /**
     * 关闭连接
     */
    public static void cleanConn() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clean(Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
