package com.example.a11708.graduationproject.NET;

import java.sql.*;

public class SQLNetService {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASS = "123456";
    public static final String URL = "jdbc:mysql://101.132.185.177:3306/userinformation?useUnicode=true&characterEncoding=UTF-8";
    private static SQLNetService p = null;
    private Connection conn = null;
    private Statement stmt = null;

    private SQLNetService() {
    }

    public static SQLNetService createInstance() {
        if (p == null) {
            p = new SQLNetService();
            p.initDB();
        }
        return p;
    }

    // 加载驱动
    public void initDB() {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 连接数据库
    public void connectDB() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // 增添/删除/修改
    public int executeUpdate(String sql) {
        int r = 0;
        try {
            r = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }


}
