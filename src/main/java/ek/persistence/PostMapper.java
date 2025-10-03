package ek.persistence;

import ek.entities.Post;
import ek.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PostMapper {



    public static Post findpost () {
        Post post = null;

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

            post = new Post(post_id,user_id,title,content,upvotes);

            }


        } catch (Exception e) {

            // 2- send fejlbesked.

            e.printStackTrace();
        }

        return post;

    }

}
