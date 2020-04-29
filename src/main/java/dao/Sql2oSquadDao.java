package dao;

import models.Squad;
import models.Player;
import org.sql2o.*;
import java.util.List;

public class Sql2oSquadDao implements SquadDao {

    private final Sql2o sql2o;

    public Sql2oSquadDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Squad squad){
        String sql = "INSERT INTO squads (squadName, jersey, strategy) VALUES (:squadName, :jersey, :strategy)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(squad)
                    .executeUpdate()
                    .getKey();
            squad.setId(id);
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Squad> getAll(){
        String sql = "SELECT * FROM squads";
        try (Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(Squad.class);
        }
    }

    @Override
    public List<Player> getAllPlayersInSquad(int squadId){
        String sql = "SELECT * FROM squads WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", squadId)
                    .executeAndFetch(Player.class);
        }
    }

    @Override
    public Squad findById(int id) {
        String sql = "SELECT * FORM squads WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Squad.class);
        }
    }

    @Override
    public void update(int id, String squadName, String jersey, String strategy) {
        String sql = "UPDATE squads SET (squadName = :squadName, jersey = :jersey, strategy = :strategy) WHERE id = :id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("squadName", squadName)
                    .addParameter("jersey", jersey)
                    .addParameter("strategy", strategy)
                    .executeUpdate();
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id){
        String sql = "REMOVE FROM squads WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM squads";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}
