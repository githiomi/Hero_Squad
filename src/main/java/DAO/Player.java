package DAO;

import java.util.List;

public interface Player {

//    get a list of all players
    List<Player> getAll();

//    add a new player
    void add(Player player);

//    get player by id
    Player getPlayerById(int id);

//    update player statistics
    void updatePlayer(int id, String newName, int newAge, String newPosition, String newDominantHand);

//    delete
    void deletePlayerById(int id);
    void deleteAllPlayers();
}
