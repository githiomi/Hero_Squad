package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SquadDaoTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createsAnInstance() {
        Squad newSquad = setUpSquad();
        assertEquals(true, newSquad instanceof Squad);
    }

    @After
    public void tearDown() throws Exception {
    }

//    easy set up
    public Squad setUpSquad() {
        return new Squad("Man u", "yellow", "Offense");
    }
}