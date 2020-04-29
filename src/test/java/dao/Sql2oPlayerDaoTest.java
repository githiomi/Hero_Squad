package dao;

import models.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.*;


import static org.junit.Assert.*;

public class Sql2oPlayerDaoTest {
    private static Sql2oSquadDao squadDao;
    private static Sql2oPlayerDao playerDao;
    private static Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/herosquad_test";
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");
        squadDao = new Sql2oSquadDao(sql2o); //ignore me for now
        playerDao = new Sql2oPlayerDao(sql2o);
        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @Test
    public void Player_canAddANewPlayerWithId () {
        Player p1 = createNew();
        int id = p1.getId();
            playerDao.add(p1);
        assertTrue(p1 instanceof Player);
        assertEquals(id, p1.getId());
    }
//
    @Test
    public void canGetASpecificPlayerUsingId() {
        Player p1 = createNew();
        Player p2 = another();
            playerDao.add(p1);
            playerDao.add(p2);
        Player retrieved = playerDao.getPlayerById(p2.getId());
        assertEquals(retrieved, p2);
    }
//
    @Test
    public void canGetAllPlayersInASquad() {
        Player p1 = createNew();
        Player p2 = another();
            playerDao.add(p1);
            playerDao.add(p2);
        int length = playerDao.getAll().size();
        assertEquals(2, length);
    }

    @Test
    public void canUpdateASinglePlayer() {
        Player p1 = createNew();
            playerDao.add(p1);
            int num = p1.getId();
        playerDao.updatePlayer(num,"Daniel", 21, "Back", "Right", 2);
        Player retrieved = playerDao.getPlayerById(num);
        assertNotEquals(retrieved, p1);
    }
//
    @Test
    public void canDeleteASinglePlayer() {
        Player p1 = createNew();
        Player p2 = another();
        int num = p2.getId();
            playerDao.add(p1);
            playerDao.add(p2);
        playerDao.deletePlayerById(num);
        int length = playerDao.getAll().size();
        assertEquals(1, length);
    }
//
    @Test
    public void canDeleteAllPlayers() {
        Player p1 = createNew();
        Player p2 = another();
            playerDao.add(p1);
            playerDao.add(p2);
        playerDao.deleteAllPlayers();
        int length = playerDao.getAll().size();
        assertEquals(0, length);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing the database");
        squadDao.deleteAll();
        playerDao.deleteAllPlayers();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("Connection closed!");
    }

//    shortcut
    public Player createNew() {
        return new Player("Daniel", 20, "Center", "Right", 1);
    }

    public Player another() {
        return new Player("Daniel", 21, "Back", "Right", 1);
    }
}