package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BattleshipModelTest {

    @Test
    public void testCollision() {
        BattleshipModel theModel = new BattleshipModel();
        assertTrue(theModel.updateShot("player", new Coords(0,0)));
    }

    @Test
    public void testMiss() {
        BattleshipModel theModel = new BattleshipModel();
        assertFalse(theModel.updateShot("player", new Coords(5,5)));
    }

    @Test
    public void testCompCollision() {
        BattleshipModel theModel = new BattleshipModel();
        assertTrue(theModel.updateShot("comp", new Coords(0,0)));
    }

    @Test
    public void testCompMiss() {
        BattleshipModel theModel = new BattleshipModel();
        assertFalse(theModel.updateShot("comp", new Coords(5,5)));
    }
}
