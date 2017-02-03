package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipModelTest {

    @Test
    // Writing tests for the sake of line coverage :{
    void testGetShipFromID() {
        BattleshipModel model = new BattleshipModel();

        assertTrue(model.getShipFromID("aircraftCarrier") != null);
        assertTrue(model.getShipFromID("battleship") != null);
        assertTrue(model.getShipFromID("cruiser") != null);
        assertTrue(model.getShipFromID("destroyer") != null);
        assertTrue(model.getShipFromID("submarine") != null);
        assertTrue(model.getShipFromID("computer_aircraftCarrier") != null);
        assertTrue(model.getShipFromID("computer_battleship") != null);
        assertTrue(model.getShipFromID("computer_cruiser") != null);
        assertTrue(model.getShipFromID("computer_destroyer") != null);
        assertTrue(model.getShipFromID("computer_submarine") != null);
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