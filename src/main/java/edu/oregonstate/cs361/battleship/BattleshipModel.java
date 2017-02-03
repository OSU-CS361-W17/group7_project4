package edu.oregonstate.cs361.battleship;

import com.sun.javafx.image.BytePixelSetter;

import java.util.ArrayList;

public class BattleshipModel {

    // Player's ships
    private Ship[] playerShips = new Ship[5];
    private Ship aircraftCarrier;
    private Ship battleship;
    private Ship cruiser;
    private Ship destroyer;
    private Ship submarine;

    // AI's ships
    private Ship[] compShips = new Ship[5];
    private Ship computer_aircraftCarrier;
    private Ship computer_battleship;
    private Ship computer_cruiser;
    private Ship computer_destroyer;
    private Ship computer_submarine;

    // Shots fired
    private ArrayList<Coords> playerHits;
    private ArrayList<Coords> playerMisses;
    private ArrayList<Coords> computerHits;
    private ArrayList<Coords> computerMisses;

    public BattleshipModel() {
        aircraftCarrier = new Ship("AircraftCarrier", 5);
        battleship = new Ship("Battleship", 4);
        cruiser = new Ship("Cruiser", 3);
        destroyer = new Ship("Destroyer", 2);
        submarine = new Ship("Submarine", 2);
        playerShips[0] = aircraftCarrier;
        playerShips[1] = battleship;
        playerShips[2] = cruiser;
        playerShips[3] = destroyer;
        playerShips[4] = submarine;

        computer_aircraftCarrier = new Ship("Computer_AircraftCarrier", 5);
        computer_battleship = new Ship("Computer_Battleship", 4);
        computer_cruiser = new Ship("Computer_Cruiser", 3);
        computer_destroyer = new Ship("Computer_Destroyer", 2);
        computer_submarine = new Ship("Computer_Submarine", 2);
        compShips[0] = computer_aircraftCarrier;
        compShips[1] = computer_battleship;
        compShips[2] = computer_cruiser;
        compShips[3] = computer_destroyer;
        compShips[4] = computer_submarine;

        playerHits = new ArrayList<Coords>();
        playerMisses = new ArrayList<Coords>();
        computerHits = new ArrayList<Coords>();
        computerMisses = new ArrayList<Coords>();
    }

    /*
     * Method for checking collisions when firing. Takes details on whether player is
     * shooting at AI or vice versa, as well as a firing coordinate. If it's a hit,
     * it updates the array list for hits, or updates array list for misses if it's not.
     * Also returns a boolean, true for hit, to it's call.
     * @param String targetSide
     * @param Coords targetArea
     * @return boolean collision
     */
    public boolean updateShot(String targetSide, Coords targetArea){
        boolean collision = false;

        if (targetSide == "comp") {
            for(int i = 0; i < compShips.length-1; i++){
                Coords start = compShips[i].getStart();
                Coords end = compShips[i].getEnd();
                boolean vert = compShips[i].checkVert();

                if(vert){
                    for (int j = 0; j < compShips[i].getLength(); j++) {
                        if(targetArea.getDown() == start.getDown()+j) {
                            collision = true;
                            break;
                        }
                    }
                }
                else {
                    for (int j = 0; j < compShips[i].getLength(); j++) {
                        if (targetArea.getAcross() == start.getAcross() + j) {
                            collision = true;
                            break;
                        }
                    }
                }
                if(collision)
                    break;
            }
                if(collision)
                    playerHits.add(targetArea);
                else
                    playerMisses.add(targetArea);
        }
        else if (targetSide == "player"){
            for(int i = 0; i < playerShips.length-1; i++){
                Coords start = playerShips[i].getStart();
                Coords end = playerShips[i].getEnd();
                boolean vert = playerShips[i].checkVert();

                if(vert){
                    for (int j = 0; j < playerShips[i].getLength(); j++) {
                        if(targetArea.getDown() == start.getDown()+j) {
                            collision = true;
                            break;
                        }
                    }
                }
                else {
                    for (int j = 0; j < playerShips[i].getLength(); j++) {
                        if (targetArea.getAcross() == start.getAcross() + j) {
                            collision = true;
                            break;
                        }
                    }
                }
                if(collision)
                    break;
            }
            if(collision)
                computerHits.add(targetArea);
            else
                computerMisses.add(targetArea);
        }
        else {
            System.err.println("Parameters not designated.");
        }
        return collision;
    }
}
