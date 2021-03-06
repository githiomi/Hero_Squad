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
        static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        // This tells our app that if Heroku sets a port for us, we need to use that port.
        // Otherwise, if they do not, continue using port 4567.

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

//        String connectionString = "jdbc:postgresql://ec2-52-86-73-86.compute-1.amazonaws.com/d6dqenmn35od3p";
//        Sql2o sql2o = new Sql2o(connectionString, "ggkndvxzdxizac", "6eaba093d5d0b7edf9b75bd5f0cc8e26f0bbe68ea5dfac5bef49ef22e5081d97");

        String connectionString = "jdbc:postgresql://localhost:5432/herosquad";
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
                int squadNumber = allSquads.size();

            model.put("squadNum", squadNumber);
            model.put("squads", allSquads);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model,"homepage.hbs");
        }, new HandlebarsTemplateEngine());

        get("/homepage", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
                List<Squad> allSquads = squadDao.getAll();
                int squadNumber = allSquads.size();

            model.put("squadNum", squadNumber);
            model.put("squads", allSquads);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "homepage.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squads", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
                List<Squad> allSquads = squadDao.getAll();
                int squadNumber = allSquads.size();

            model.put("squads", allSquads);
            model.put("numberOfSquads", squadNumber);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "squadsview.hbs");
        }, new HandlebarsTemplateEngine());

        post("/squads", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
                String teamName = request.queryParams("teamName");
                String jersey = request.queryParams("jerseyColor");
                String strategy = request.queryParams("strategy");

                Squad newSquad = new Squad(teamName, jersey, strategy);
                squadDao.add(newSquad);
                List<Squad> allSquads = squadDao.getAll();
                int squadNumber = squadDao.getAll().size();

            model.put("numberOfSquads", squadNumber);
            model.put("username", request.session().attribute("username"));
            model.put("squads", allSquads);
            return new ModelAndView(model, "squadsview.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squads/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
                List<Squad> allSquads = squadDao.getAll();

            model.put("squads", allSquads);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "newsquad.hbs");
        },new HandlebarsTemplateEngine());

        get("/squads/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
                squadDao.deleteAll();
                playerDao.deleteAllPlayers();
            res.redirect("/squads");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Integer squadId = Integer.parseInt(req.params("id"));

                Squad specificSquad = squadDao.findById(squadId);
                List<Player> allPlayers = squadDao.getAllPlayersInSquad(squadId);
                int playerLength = squadDao.getAllPlayersInSquad(squadId).size();

            model.put("length", playerLength);
            model.put("username", req.session().attribute("username"));
            model.put("squad", specificSquad);
            model.put("players", allPlayers);
            return new ModelAndView(model, "squaddetails.hbs");
        }, new HandlebarsTemplateEngine());

        get("squads/:id/players/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params("id"));

                List<Squad> allSquads = squadDao.getAll();
                Squad belonging = squadDao.findById(id);

            model.put("thisSquad", belonging);
            model.put("squads", allSquads);
            return new ModelAndView(model, "newplayer.hbs");
        }, new HandlebarsTemplateEngine());

        get("squads/:ie/players/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int squadId = Integer.parseInt(req.params("ie"));
            int playerId = Integer.parseInt(req.params("id"));

                Squad specificSquad = squadDao.findById(squadId);
                Player specificPlayer = playerDao.getPlayerById(playerId);

           model.put("squad", specificSquad);
           model.put("players", specificPlayer);
           return new ModelAndView(model, "playerdetails.hbs");
        }, new HandlebarsTemplateEngine());

        get("squads/:id/players", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int squadId = Integer.parseInt(req.params("id"));

                Squad squad = squadDao.findById(squadId);
                int players = playerDao.getAll().size();
                List<Player> allPlayers = playerDao.getAll();

           model.put("squad", squad);
           model.put("username", req.session().attribute("username"));
           model.put("playersNumber", players);
           model.put("players", allPlayers);
           return new ModelAndView(model, "players.hbs");
        }, new HandlebarsTemplateEngine());

        post("/squads/:id/players", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int squadBelongs = Integer.parseInt(req.params("id"));

                String name = req.queryParams("name");
                int age = Integer.parseInt(req.queryParams("age"));
                String position = req.queryParams("position");
                String hand = req.queryParams("hand");
                int squadId = Integer.parseInt(req.queryParams("squad"));

                Player newPlayer = new Player(name, age, position, hand, squadId);
                playerDao.add(newPlayer);

                Squad squad_n = squadDao.findById(squadBelongs);
                Squad squad = squadDao.findById(squadId);
                int players = playerDao.getAll().size();
                List<Player> allPlayers = playerDao.getAll();

            model.put("squads", squad_n);
            model.put("squad", squad);
            model.put("username", req.session().attribute("username"));
            model.put("playersNumber", players);
            model.put("players", allPlayers);
            return new ModelAndView(model, "players.hbs");
        }, new HandlebarsTemplateEngine());

        get("squads/:ie/players/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int squadId = Integer.parseInt(req.params("ie"));
            int playerId = Integer.parseInt(req.params("id"));
                playerDao.deletePlayerById(playerId);
            res.redirect("/squads/" + squadId);
            return null;
        }, new HandlebarsTemplateEngine());

        get("squads/:id/players/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int squadToDeleteFrom = Integer.parseInt(req.params("id"));

                playerDao.deleteAllPlayers();
                res.redirect("/squads/" + squadToDeleteFrom);
             return null;
        }, new HandlebarsTemplateEngine());

        get("squads/:id/update", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            res.redirect("/squads/" + id);
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
