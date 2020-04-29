package dao;

import models.Squad;
import models.Player;

import java.util.List;

public interface SquadDao {

//    get all squads
    List<Squad> getAll();

//    add a new squad
    void add(Squad squad);

//    get squad by id
    Squad findById(int id);

//    update a single squad
    void update(int squadId, String newName, String newJerseyColor, String newStrategy);

//    get all players in team
    List<Player> getAllPlayersInSquad(int squadId);

//    delete
    void deleteById(int id);
    void deleteAll();

}
