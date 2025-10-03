package ek.controller;

import ek.entities.Post;
import ek.persistence.PostMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class PostController {
    public static void addRoutes(Javalin app) {
        app.post("/posts/upvote/{id}", ctx -> {
            int postId = Integer.parseInt(ctx.pathParam("id"));
            PostMapper.upvote(postId);

            ctx.redirect("/main");
        });

            app.post("/posts/downvote/{id}", ctx -> {
                int postId = Integer.parseInt(ctx.pathParam("id"));
                PostMapper.downvote(postId);
                ctx.redirect("/main");
            });
        }
    ;




    private static void downvote(int postId) {
        PostMapper.downvote(postId);
    }

    private static void upvote(int postId) {
        PostMapper.upvote(postId);

    }
}


