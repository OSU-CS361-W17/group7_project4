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
    public void shootingTest() {
        //Create a model with non-random ships
        BattleshipModel theModel = new BattleshipModel(true);
        assertTrue(theModel.getPlayerHits().size() == 0 && theModel.getPlayerMisses().size() == 0);

        theModel.updateShot("player", theModel.getComputerFireCoords());
        assertTrue(theModel.getPlayerHits().size() == 1 || theModel.getPlayerMisses().size() == 1);

        assertTrue(theModel.updateShot("computer",new Coords(1,1)));
        assertFalse(theModel.updateShot("computer",new Coords(11,11)));

        assertTrue(theModel.updateShot("computer", new Coords(1,5)));

        theModel.updateShipPosition("player","aircraftCarrier",1,1,"horizontal");
        assertTrue(theModel.updateShot("player",new Coords(1,1)));
        assertFalse(theModel.updateShot("player",new Coords(11,11)));

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
    public void aiPlaceTest() {

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
    
    @Test
    public void nullCoordsShotCheck() {
        BattleshipModel theModel = new BattleshipModel();

        assertFalse(theModel.updateShot("player", null));
        assertFalse(theModel.updateShot("comp", null));
    }
}
