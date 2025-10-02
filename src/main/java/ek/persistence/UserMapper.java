package ek.persistence;

import ek.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserMapper
{
    public static User login(String username, String password) {
        User user = null;

        String query = "SELECT * FROM users WHERE user_name = ? AND password = ?";

        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // WUHU vi har fundet match!
                user = new User(username, password);
            }
            else
            {
                System.out.println("konto ikke fundet");
                // fejl håndtér at der ikke er fundet nogen forekomst med det pågældende navn og kode i databasen.
            }
        } catch (Exception e) {

            // 2- send fejlbesked.

            e.printStackTrace();
        }

        return user;
    }



    public static User createUser(String username, String password) {


        String query = "INSERT INTO users (user_name,password) VALUES (?, ? )";
        User user = null;
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);


            user = new User(username, password);

            int rows = ps.executeUpdate();
            System.out.println(rows + " student inserted successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}