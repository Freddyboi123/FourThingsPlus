package ek.controller;

import ek.entities.User;
import ek.persistence.ConnectionPool;
import ek.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class UserController {

    public static void addRoutes(Javalin app) {


    }

    public static void logout(@NotNull Context ctx) {
        ctx.sessionAttribute("currentUser", null);
        ctx.redirect("/");
    }

    public static void login(Context ctx, ConnectionPool cnp) {
        // 1. find username og password som brugeren har indtastet
        String username = ctx.formParam("user_name");
        String password = ctx.formParam("password");

        // 2. tjek om det eksisterer i databasen!
        // 2+ lav en user instans og sæt ctx.currentUser = user.
        User user = UserMapper.login(username, password, cnp);
        if (user != null) {
            ctx.redirect("/main");
            ctx.sessionAttribute("currentUser", user);


            //jave: find current user
//            User currentUser = ctx.sessionAttribute("currentUser");
//            if (currentUser != null) {
//                System.out.println("Logged in as: " + currentUser.getUsername());
//            }
        } else {
            ctx.redirect("/index.html");
        }
    }

    public static void createUser(Context ctx, ConnectionPool cnp) {


        String username = ctx.formParam("user_name");
        String password = ctx.formParam("password");


        User user = UserMapper.createUser(username, password, cnp);
        ctx.redirect("/");

    }
}
