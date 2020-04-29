package dao;

import java.util.List;
import models.Player;

public interface PlayerDao {

//    get a list of all players
    List<Player> getAll();

//    add a new player
    void add(Player player);

//    get player by id
    Player getPlayerById(int id);

//    update player statistics
    void updatePlayer(String name, int age, String position, String dominantHand, int id);

//    delete
    void deletePlayerById(int id);
    void deleteAllPlayers();
}
