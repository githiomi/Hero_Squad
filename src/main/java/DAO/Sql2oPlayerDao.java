package DAO;

import org.sql2o.*;
import models.Player;
import java.util.List;

public class Sql2oPlayerDao implements PlayerDao {

    private final Sql2o sql2o;

    public Sql2oPlayerDao (Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Player player) {
        String sql = "INSERT INTO players (name, age, position, dominantHand, squadId) VALUES (:name, :age, :position, :dominantHand, :squadId)";
        try (Connection conn = sql2o.open() ){
            int id = (int) conn.createQuery(sql, true)
                    .bind(player)
                    .executeUpdate()
                    .getKey();
            player.setSquadId(id);
        }
    }

    @Override
    public List<Player> getAll() {
        String sql = "SELECT * FROM players";
        try (Connection con = sql2o.open() ) {
            return con.createQuery(sql)
                    .executeAndFetch(Player.class);
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Player getPlayerById(int id){
        String sql = "SELECT * FORM squads WHERE id = :id";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void updatePlayer(String name, int age, String position, String dominantHand, int id){
        String sql = "UPDATE players SET (name = :name, age = :age, position = :position, dominantHand = :dominantHand) WHERE id = :id";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("age", age)
                    .addParameter("position", position)
                    .addParameter("dominantHand", dominantHand)
                    .executeUpdate();
        }
    }

    @Override
    public void deletePlayerById(int id) {
        String sql = "REMOVE FROM players WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAllPlayers() {
        String sql = "REMOVE FROM players";
        try (Connection conn = sql2o.open() ){
            conn.createQuery(sql)
                    .executeUpdate();
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}
