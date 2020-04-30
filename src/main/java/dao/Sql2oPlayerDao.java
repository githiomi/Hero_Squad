package dao;

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
            player.setId(id);
        }
        catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Player> getAll() {
        String sql = "SELECT * FROM players";
        try (Connection con = sql2o.open() ) {
            return con.createQuery(sql)
                    .executeAndFetch(Player.class);
        }
    }

    @Override
    public Player getPlayerById(int id) {
        String sql = "SELECT * FROM players WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Player.class);
        }
    }

    @Override
    public void updatePlayer(int id, String name, int age, String position, String dominantHand, int squadId){
        String sql = "UPDATE players SET (name, age, position, dominantHand, squadId) = (:name, :age, :position, :dominantHand, :squadId) WHERE id = :id";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("squadId", squadId)
                    .addParameter("name", name)
                    .addParameter("age", age)
                    .addParameter("position", position)
                    .addParameter("dominantHand", dominantHand)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public void deletePlayerById(int id) {
        String sql = "DELETE from players WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAllPlayers() {
        String sql = "DELETE from players";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}
