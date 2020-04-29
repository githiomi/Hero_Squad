package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void Player_createsInstanceOfPlayer() {
        Player newP = setUpPlayer();
        assertEquals(true, newP instanceof Player);
    }

    @After
    public void tearDown() throws Exception {
    }

//    setup
    public Player setUpPlayer() {
        return new Player("me", 20, "Setter", "right", 1);
    }
}