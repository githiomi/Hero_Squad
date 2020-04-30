package dao;

import models.Player;
import models.Squad;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oSquadDaoTest {
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
    public void Squad_canAddToSquad() {
        Squad s1 = setupNew();
            squadDao.add(s1);
        int num = s1.getId();
        assertEquals(num, s1.getId());
    }

    @Test
    public void canFindSpecificCategory() {
        Squad s1 = setupNew();
            squadDao.add(s1);
        Squad retrieved = squadDao.findById(s1.getId());
        assertEquals(retrieved, s1);
    }

    @Test
    public void canGetAllSquads() {
        Squad s1 = setupNew();
        Squad s2 = setupNew();
            squadDao.add(s1);
            squadDao.add(s2);
        int length = squadDao.getAll().size();
        assertEquals(2, length);
    }

    @Test
    public void canUpdateASquadById() {
        Squad s1 = setupNew();
        String squadName = "Manchester City";
        String jersey = "Black";
        String strategy = "Offensive";
            squadDao.add(s1);
        squadDao.update(s1.getId(), squadName, jersey, strategy);
        Squad updated = squadDao.findById(s1.getId());
        assertEquals(updated, squadDao.findById(updated.getId()));
    }

    @Test
    public void canGetAllPlayersInSquad() {
        Squad s1 = setupNew();
            squadDao.add(s1);
            int squad = s1.getId();
        Player p1 = new Player("Dhosio", 20, "Center", "Right", squad);
        Player p2 = new Player("Daniel", 21, "Back", "Left", squad);
            playerDao.add(p1);
            playerDao.add(p2);
            assertEquals(true, playerDao.getAll().contains(p1));
            assertEquals(s1.getId(), squad);
        assertEquals(2, squadDao.getAllPlayersInSquad(squad).size());
        assertEquals(true, squadDao.getAllPlayersInSquad(squad).contains(p1));
        assertEquals(true, squadDao.getAllPlayersInSquad(squad).contains(p2));
    }

    @Test
    public void canDeleteASquad() {
        Squad s1 = setupNew();
        Squad s2 = new Squad ("Man C", "Blue", "Offensive");
            squadDao.add(s1);
            squadDao.add(s2);
        squadDao.deleteById(s2.getId());
        int length = squadDao.getAll().size();
        assertEquals(1, length);
    }

    @Test
    public void canDeleteAllSquads() {
        Squad s1 = setupNew();
        Squad s2 = new Squad ("Man C", "Blue", "Offensive");
            squadDao.add(s1);
            squadDao.add(s2);
        int length = squadDao.getAll().size();
        squadDao.deleteAll();
        assertTrue( length > 0 && length > squadDao.getAll().size());
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
    public models.Squad setupNew(){
        return new Squad("Man U", "Red", "Attack");
    }
}