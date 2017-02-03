package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipModelTest {

    @Test
    // I hate writing tests for the sake of line coverage, kill me
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
}