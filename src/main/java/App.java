import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oSquadDao;
import dao.Sql2oPlayerDao;
import models.Squad;
import models.Player;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    //    static int getHerokuAssignedPort() {
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        if (processBuilder.environment().get("PORT") != null) {
//            return Integer.parseInt(processBuilder.environment().get("PORT"));
//        }
//        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
//    }
    public static void main(String[] args) {

//        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        String connectionString = "jdbc:postgresql://localhost:5432/herosquad"; //connect to todolist, not todolist_test!
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");

        Sql2oSquadDao squadDao = new Sql2oSquadDao(sql2o);
        Sql2oPlayerDao playerDao = new Sql2oPlayerDao(sql2o);

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/homepage", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String enteredUsername = req.queryParams("username");
            req.session().attribute("username", enteredUsername);
                List<Squad> allSquads = squadDao.getAll();

            model.put("squads", allSquads);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model,"homepage.hbs");
//            return null;
        }, new HandlebarsTemplateEngine());

        get("/squads/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
                List<Squad> allSquads = squadDao.getAll();
                int squads = allSquads.size();

            model.put("numberOfSquads", squads);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "squadsview.hbs");
        });
    }
}
