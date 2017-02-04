package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

        assertTrue(theModel.updateShot("computer",new Coords(0,0)));
        assertFalse(theModel.updateShot("computer",new Coords(1,1)));

        theModel.updateShipPosition("computer","computer_aircraftCarrier",2,1,"vertical");
        assertTrue(theModel.updateShot("computer", new Coords(1,2)));

        assertTrue(theModel.updateShot("player",new Coords(0,0)));
        assertFalse(theModel.updateShot("player",new Coords(1,1)));

        theModel.updateShipPosition("player","aircraftCarrier",3,1,"vertical");
        assertTrue(theModel.updateShot("player",new Coords(1,3)));
        
        //System.err.print() testing code from Stack-Overflow
        ByteArrayOutputStream errorTest = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorTest));

        theModel.updateShot(null, new Coords(1,1));

        assertEquals("Parameters not designated.", errorTest.toString());
        //End Stack-Overflow code
    }
    @Test
    public void aiPlaceTest(){
        BattleshipModel theModel = new BattleshipModel();
        Ship aircraftCarrier = theModel.getShipFromName("aircraftCarrier");
        assertFalse(aircraftCarrier.getStart().getAcross() == 0);
        Ship destroyer = theModel.getShipFromName("destroyer");
        assertFalse(destroyer.getStart().getAcross() == 0);
        Ship cruiser = theModel.getShipFromName("cruiser");
        assertFalse(cruiser.getStart().getAcross() == 0);
        Ship battleship = theModel.getShipFromName("battleship");
        assertFalse(battleship.getStart().getAcross() == 0);
        Ship submarine = theModel.getShipFromName("submarine");
        assertFalse(submarine.getStart().getAcross() == 0);
    }
}