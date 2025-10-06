package ek;
import ek.config.ThymeleafConfig;

import ek.controller.UserController;
import ek.entities.Post;
import ek.entities.User;
import ek.persistence.ConnectionPool;
import ek.persistence.PostMapper;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.List;
import java.util.Map;




public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "four_things_plus";
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
            config.staticFiles.add("/templates");
        }).start(7079);

        app.post("/login", ctx -> UserController.login(ctx,connectionPool));
        app.post("/createuser", ctx -> UserController.createUser(ctx,connectionPool));
        app.post("/logout", ctx -> UserController.logout(ctx));

        app.post("/posts/upvote/{id}", ctx -> {
            int postId = Integer.parseInt(ctx.pathParam("id"));
            PostMapper.upvote(postId,connectionPool);

            ctx.redirect("/main");
        });

        app.post("/posts/downvote/{id}", ctx -> {
            int postId = Integer.parseInt(ctx.pathParam("id"));
            PostMapper.downvote(postId,connectionPool);
            ctx.redirect("/main");
        });

        app.get("/main", ctx -> {
            User currentUser = ctx.sessionAttribute("currentUser");

            List<Post> posts = PostMapper.findpost(connectionPool); // ✅ now fetch all posts

            ctx.render("main_page.html", Map.of(
                    "user", currentUser,
                    "posts", posts
            ));
        });


    }
}