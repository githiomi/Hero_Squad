package DAO;

import Blueprints.Player;
import java.util.List;

public interface Squad {

//    get all squads
    List<Squad> getAll();

//    add a new squad
    void add(Squad squad);

//    get squad by id
    Squad findById();

//    update a single squad
    void update(int squadId, String newName, String newJerseyColor, String newStrategy);

//    get all teams
    List<Player> getAllPlayersInSquad(int squadId);

//    delete
    void deleteById(int squadId);
    void deleteAll();
}
