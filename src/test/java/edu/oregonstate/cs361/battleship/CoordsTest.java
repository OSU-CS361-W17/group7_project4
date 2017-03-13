package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static edu.oregonstate.cs361.battleship.Coords.Direction.*;
import static edu.oregonstate.cs361.battleship.BattleshipModel.GRID_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class CoordsTest {

    @Test
    void testCoords() {
        Coords coords1 = new Coords();
        // Should default to position 0,0 (off-grid)
        assertEquals(0, coords1.getDown());
        assertEquals(0, coords1.getAcross());

        Coords coords2 = new Coords(1, 4);
        assertEquals(4, coords2.getAcross());
        assertEquals(1, coords2.getDown());
    }

    @Test
    void testSetters() {
        Coords coords = new Coords();

        coords.setAcross(2);
        assertEquals(2, coords.getAcross());
        coords.setDown(1);
        assertEquals(1, coords.getDown());
    }

    @Test
    void testNegatives() {
        Coords coords = new Coords(-2, -4);
        assertTrue(coords.getAcross() >= 0);
        assertTrue(coords.getDown() >= 0);

        coords.setAcross(-3);
        coords.setDown(-1);
        assertTrue(coords.getAcross() >= 0);
        assertTrue(coords.getDown() >= 0);
    }

    @Test
    void testOnGrid() {
        Coords onGrid = new Coords (6, 4);
        Coords offGrid1 = new Coords (GRID_SIZE + 2, 3);
        Coords offGrid2 = new Coords (9, 0);

        assertTrue(onGrid.onGrid());
        assertFalse(offGrid1.onGrid());
        assertFalse(offGrid2.onGrid());
    }

    @Test
    void testGetAdjacents() {
        Coords coords = new Coords(9, 3);
        Coords[] adjacents = coords.getAdjacents();

        assertTrue(adjacents[0].getAcross() == coords.getAcross());
        assertTrue(adjacents[0].getDown() == coords.getDown() - 1);

        assertTrue(adjacents[1].getAcross() == coords.getAcross() + 1);
        assertTrue(adjacents[1].getDown() == coords.getDown());

        assertTrue(adjacents[2].getAcross() == coords.getAcross());
        assertTrue(adjacents[2].getDown() == coords.getDown() + 1);

        assertTrue(adjacents[3].getAcross() == coords.getAcross() - 1);
        assertTrue(adjacents[3].getDown() == coords.getDown());
    }

    @Test
    void testGetAbove() {
        Coords coords = new Coords(8, 3);
        Coords oneAbove = coords.getAbove();

        assertTrue(oneAbove.getAcross() == coords.getAcross());
        assertTrue(oneAbove.getDown() == coords.getDown() - 1);
    }

    @Test
    void testGetBelow() {
        Coords coords = new Coords(8, 3);
        Coords oneBelow = coords.getBelow();

        assertTrue(oneBelow.getAcross() == coords.getAcross());
        assertTrue(oneBelow.getDown() == coords.getDown() + 1);
    }

    @Test
    void testGetRight() {
        Coords coords = new Coords(8, 3);
        Coords oneRight = coords.getRight();

        assertTrue(oneRight.getAcross() == coords.getAcross() + 1);
        assertTrue(oneRight.getDown() == coords.getDown());
    }

    @Test
    void testGetLeft() {
        Coords coords = new Coords(8, 3);
        Coords oneLeft = coords.getLeft();

        assertTrue(oneLeft.getAcross() == coords.getAcross() - 1);
        assertTrue(oneLeft.getDown() == coords.getDown());
    }

    @Test
    void testGetInDirection() {
        Coords coords1 = new Coords(6, 5);

        Coords fourAbove = coords1.getInDirection(UP, 4);
        Coords oneDown = coords1.getInDirection(DOWN);
        Coords twoLeft = coords1.getInDirection(LEFT, 2);
        Coords twoRight = coords1.getInDirection(RIGHT, 2);

        assertTrue(fourAbove.getAcross() == coords1.getAcross());
        assertTrue(fourAbove.getDown() == coords1.getDown() - 4);

        assertTrue(oneDown.getAcross() == coords1.getAcross());
        assertTrue(oneDown.getDown() == coords1.getDown() + 1);

        assertTrue(twoLeft.getAcross() == coords1.getAcross() - 2);
        assertTrue(twoLeft.getDown() == coords1.getDown());

        assertTrue(twoRight.getAcross() == coords1.getAcross() + 2);
        assertTrue(twoRight.getDown() == coords1.getDown());
    }

    @Test
    void testEquals() {
        Coords coords1 = new Coords(7, 3);
        Coords coords2 = new Coords(7, 3);
        assertTrue(coords1.equals(coords2));
    }
}