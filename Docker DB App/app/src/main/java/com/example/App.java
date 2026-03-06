package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class App {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://db:5432/dockerdb";
        String user = "postgres";
        String password = "postgres";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO users(name,email) VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, "Rahul");
            stmt.setString(2, "rahul@gmail.com");

            stmt.executeUpdate();

            System.out.println("Record inserted successfully!");

            conn.close();

        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }
}