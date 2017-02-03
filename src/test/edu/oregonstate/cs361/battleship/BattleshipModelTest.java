package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipModelTest {

    @Test
    // Writing tests for the sake of line coverage :(
    void testGetShipFromName() {
        BattleshipModel model = new BattleshipModel();

        assertTrue(model.getShipFromName("aircraftCarrier") != null);
        assertTrue(model.getShipFromName("battleship") != null);
        assertTrue(model.getShipFromName("cruiser") != null);
        assertTrue(model.getShipFromName("destroyer") != null);
        assertTrue(model.getShipFromName("submarine") != null);
        assertTrue(model.getShipFromName("computer_aircraftCarrier") != null);
        assertTrue(model.getShipFromName("computer_battleship") != null);
        assertTrue(model.getShipFromName("computer_cruiser") != null);
        assertTrue(model.getShipFromName("computer_destroyer") != null);
        assertTrue(model.getShipFromName("computer_submarine") != null);
        }
        
    @Test
    public void testMiss() {
        BattleshipModel theModel = new BattleshipModel();
        assertFalse(theModel.updateShot("player", new Coords(5,5)));
    }

    @Test
    public void testCompCollision() {
        BattleshipModel theModel = new BattleshipModel();
        assertTrue(theModel.updateShot("computer", new Coords(0,0)));
    }

    @Test
    public void testCompMiss() {
        BattleshipModel theModel = new BattleshipModel();
        assertFalse(theModel.updateShot("computer", new Coords(5,5)));
    }

    @Test
    public void compShotTest() {
        BattleshipModel theModel = new BattleshipModel();
        assertTrue(theModel.getComputerHits().size() == 0 && theModel.getComputerMisses().size() == 0);

        theModel.updateShot("computer", new Coords(1, 1) );

        assertTrue(theModel.getComputerHits().size() == 1 || theModel.getComputerMisses().size() == 1);
    }

    @Test
    public void playerShotTest() {
        BattleshipModel theModel = new BattleshipModel();
        assertTrue(theModel.getPlayerHits().size() == 0 && theModel.getPlayerMisses().size() == 0);

        theModel.updateShot("player", theModel.getComputerFireCoords());

        assertTrue(theModel.getPlayerHits().size() == 1 || theModel.getPlayerMisses().size() == 1);
    }
}