package ek;
import ek.config.ThymeleafConfig;
import ek.controller.UserController;
import ek.entities.User;
import ek.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.Map;


public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "four_things_plus";
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args)
    {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
            config.staticFiles.add("/templates");
        }).start(7079);




        UserController.addRoutes(app);



        app.get("/main", ctx -> {
            User currentUser = ctx.sessionAttribute("currentUser");
            ctx.render("main_page.html", Map.of("user", currentUser));
        });
        app.get("/", ctx -> ctx.render("index.html"));

    }
}