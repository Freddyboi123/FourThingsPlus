package ek.persistence;

import ek.entities.Post;
import ek.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostMapper {



    public static List<Post> findpost () {
        List<Post> posts = new ArrayList<>();


        String query = "SELECT * FROM post JOIN users using (user_id)";

        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
            int post_id =rs.getInt("post_id");
            String user_id =rs.getString("user_name");
            String title =rs.getString("title");
            String content =rs.getString("content");
            int upvotes =rs.getInt("upvotes");

            posts.add(new Post(post_id,user_id,title,content,upvotes));

            }


        } catch (Exception e) {

            // 2- send fejlbesked.

            e.printStackTrace();
        }

        return posts;

    }
    public static void upvote(int postId){

        String query = "UPDATE post SET upvotes = upvotes + 1  WHERE post_id = ?";

        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,postId);

            int rows = ps.executeUpdate();
            System.out.println("Rows updated: " + rows); // 👈 debug
        } catch (Exception e) {

            // 2- send fejlbesked.

            e.printStackTrace();
        }

    }


    public static void downvote(int postId){

        String query = "UPDATE post SET upvotes = upvotes - 1  WHERE post_id = ?";

        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,postId);

            int rows = ps.executeUpdate();
            System.out.println("Rows updated: " + rows); // 👈 debug
        } catch (Exception e) {

            // 2- send fejlbesked.

            e.printStackTrace();
        }

    }

}

