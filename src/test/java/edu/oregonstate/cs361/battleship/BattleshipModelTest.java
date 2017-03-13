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
        assertTrue(model.getShipFromName("clipper") != null);
        assertTrue(model.getShipFromName("dinghy") != null);
        assertTrue(model.getShipFromName("submarine") != null);
        assertTrue(model.getShipFromName("computer_aircraftCarrier") != null);
        assertTrue(model.getShipFromName("computer_battleship") != null);
        assertTrue(model.getShipFromName("computer_clipper") != null);
        assertTrue(model.getShipFromName("computer_dinghy") != null);
        assertTrue(model.getShipFromName("computer_submarine") != null);
        }

    @Test
    public void shootingTest() {
        //Create a model with non-random ships
        BattleshipModel theModel = new BattleshipModel(true);
        assertTrue(theModel.getPlayerHits().size() == 0 && theModel.getPlayerMisses().size() == 0);

        theModel.updateShot("player", theModel.getComputerFireCoords());
        assertTrue(theModel.getPlayerHits().size() == 1 || theModel.getPlayerMisses().size() == 1);

        assertTrue(theModel.updateShot("computer",new Coords(1, 1)));
        assertFalse(theModel.updateShot("computer",new Coords(11, 11)));

        assertTrue(theModel.updateShot("computer", new Coords(5, 1)));

        theModel.updateShipPosition("player","aircraftCarrier",1,1,"horizontal");
        assertTrue(theModel.updateShot("player",new Coords(1, 1)));
        assertFalse(theModel.updateShot("player",new Coords(11, 11)));

        theModel.updateShipPosition("player","aircraftCarrier",3,1,"vertical");
        assertTrue(theModel.updateShot("player",new Coords(3, 1)));
    }

    @Test
    public void sinkCivilianShipTest() {
        BattleshipModel model = new BattleshipModel(false);
        model.updateShipPosition("player", "clipper", 2, 2, "vertical");

        Coords target = new Coords(2, 2);
        model.updateShot("player", target);

        // There should be hits on every tile of the Clipper, because it's a civilian ship, and sinks after one hit
        for (Coords coords : model.getShipFromName("clipper").getCoordsArray()) {
            assertTrue(model.getPlayerHits().contains(coords));
        }

        assertTrue(model.getShipFromName("clipper").checkSunk());
    }

    @Test
    public void hardDifficultyFiringTest() {
        BattleshipModel model = new BattleshipModel(false);
        Coords target = new Coords(4, 5);

        model.updateShipPosition("player", "aircraftCarrier", 4,5, "horizontal");
        model.updateShot("player", target); // Initial shot, AI should begin targeting this ship

        // Should sink within ship's length + 3 shots (but in this case will sink after 6)
        for (int i = 0; i < model.getShipFromName("aircraftCarrier").getLength() - 1 + 3; i++) {
            target = model.getComputerFireCoords();
            model.updateShot("player", target);
        }

        assertTrue(model.getShipFromName("aircraftCarrier").checkSunk());
    }

    @Test
    public void aiPlaceTest() {

        BattleshipModel theModel = new BattleshipModel();
        Ship aircraftCarrier = theModel.getShipFromName("computer_aircraftCarrier");
        assertFalse(aircraftCarrier.getStart().getAcross() == 0);
        Ship dinghy = theModel.getShipFromName("computer_dinghy");
        assertFalse(dinghy.getStart().getAcross() == 0);
        Ship clipper = theModel.getShipFromName("computer_clipper");
        assertFalse(clipper.getStart().getAcross() == 0);
        Ship battleship = theModel.getShipFromName("computer_battleship");
        assertFalse(battleship.getStart().getAcross() == 0);
        Ship submarine = theModel.getShipFromName("computer_submarine");
        assertFalse(submarine.getStart().getAcross() == 0);
    }
    
    @Test
    public void nullCoordsShotCheck() {
        BattleshipModel theModel = new BattleshipModel();

        assertFalse(theModel.updateShot("player", null));
        assertFalse(theModel.updateShot("computer", null));
    }

    @Test
    public void scanTest() {
        BattleshipModel model = new BattleshipModel(true);

        model.scan(4, 2);
        assertTrue(model.getScanResult());

        model.scan(9,9);
        assertFalse(model.getScanResult());
    }


}
