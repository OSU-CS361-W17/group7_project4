package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.Random;

public class BattleshipModel {

    // Declares a Constant for size of the game grid.
    public static final int GRID_SIZE = 10;

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

    // Tracks AI's remaining fireable Coords
    private ArrayList<Coords> computerRemainingFirableCoords;


    public BattleshipModel() {
        aircraftCarrier = new Ship("aircraftCarrier", 5);
        battleship = new Ship("battleship", 4);
        cruiser = new Ship("cruiser", 3);
        destroyer = new Ship("destroyer", 2);
        submarine = new Ship("submarine", 2);
        playerShips[0] = aircraftCarrier;
        playerShips[1] = battleship;
        playerShips[2] = cruiser;
        playerShips[3] = destroyer;
        playerShips[4] = submarine;

        computer_aircraftCarrier = new Ship("computer_aircraftCarrier", 5);
        computer_battleship = new Ship("computer_battleship", 4);
        computer_cruiser = new Ship("computer_cruiser", 3);
        computer_destroyer = new Ship("computer_destroyer", 2);
        computer_submarine = new Ship("computer_submarine", 2);
        compShips[0] = computer_aircraftCarrier;
        compShips[1] = computer_battleship;
        compShips[2] = computer_cruiser;
        compShips[3] = computer_destroyer;
        compShips[4] = computer_submarine;

        playerHits = new ArrayList<Coords>();
        playerMisses = new ArrayList<Coords>();
        computerHits = new ArrayList<Coords>();
        computerMisses = new ArrayList<Coords>();

        //Calls for clean new AI fireable array.
        setCleanComputerShotArray();
    }

    /*
    Is used to create a new, clean shot array for the
    AI to reference on making shot decisions.
     */
    public void setCleanComputerShotArray() {

        //Sets the shot array's size based on GRID_SIZE.
        computerRemainingFirableCoords = new ArrayList<Coords>(GRID_SIZE * GRID_SIZE);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++){
                computerRemainingFirableCoords.add(new Coords(i+1,j+1));
            }
        }
    }

    /*
    Simple getters to return out player and computer
    hits and misses.
    */
    public ArrayList<Coords> getPlayerHits() {
        return playerHits;
    }

    public ArrayList<Coords> getPlayerMisses() {
        return playerMisses;
    }

    public ArrayList<Coords> getComputerHits() {
        return computerHits;
    }

    public ArrayList<Coords> getComputerMisses() {
        return computerMisses;
    }


    /*row, column
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

        if (targetArea == null)
            return false;

        if (targetSide == "comp") {
            for(int i = 0; i < compShips.length; i++){
                Coords start = compShips[i].getStart();
                boolean vert = compShips[i].checkVert();

                if(vert){
                    for (int j = 0; j < compShips[i].getLength(); j++) {
                        if(targetArea.getDown() == start.getDown()+j && targetArea.getAcross() == start.getAcross()) {
                            collision = true;
                            break;
                        }
                    }
                }
                else {
                    for (int j = 0; j < compShips[i].getLength(); j++) {
                        if (targetArea.getAcross() == start.getAcross() + j && targetArea.getDown() == start.getDown()) {
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
        else if (targetSide == "player"){
            for(int i = 0; i < playerShips.length; i++){
                Coords start = playerShips[i].getStart();
                boolean vert = playerShips[i].checkVert();

                if(vert){
                    for (int j = 0; j < playerShips[i].getLength(); j++) {
                        if(targetArea.getDown() == start.getDown()+j && targetArea.getAcross() == start.getAcross()) {
                            collision = true;
                            break;
                        }
                    }
                }
                else {
                    for (int j = 0; j < playerShips[i].getLength(); j++) {
                        if (targetArea.getAcross() == start.getAcross() + j  && targetArea.getDown() == start.getDown()) {
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
        else {
            System.err.println("Parameters not designated.");
        }
        return collision;
    }


    public void updateShipPosition(String name, int row, int column, String orientation) {
        Ship ship = getShipFromName(name);
        ship.updatePosition(row, column, orientation);

        for (int i = 0; i < playerShips.length; i++) {
            if (name.equals(playerShips[i].getName())) {
                playerShips[i].updatePosition(row, column, orientation);
            }
        }

        for (int i = 0; i < compShips.length; i++) {
            if (name.equals(compShips[i].getName())) {
                compShips[i].updatePosition(row, column, orientation);
            }
        }
    }

    // Makes it possible to retrieve ships from strings of their name
    Ship getShipFromName(String shipID) {
        Ship ship = null;
        switch (shipID) {
            case "aircraftCarrier": ship = aircraftCarrier; break;
            case "battleship": ship = battleship; break;
            case "cruiser": ship = cruiser; break;
            case "destroyer": ship = destroyer; break;
            case "submarine": ship = submarine; break;
            case "computer_aircraftCarrier": ship = computer_aircraftCarrier; break;
            case "computer_battleship": ship = computer_battleship; break;
            case "computer_cruiser": ship = computer_cruiser; break;
            case "computer_destroyer": ship = computer_destroyer; break;
            case "computer_submarine": ship = computer_submarine; break;
        }
        return ship;
    }


    /*
    Generates a shot for the AI, and then
    returns it as a Coords object.
    @return Coords
    */
    public Coords getComputerFireCoords() {

        // New Random object to generate random shots
        Random randNum = new Random();

        if (computerRemainingFirableCoords.size() != 0) {
            int shotArrayNum = randNum.nextInt(computerRemainingFirableCoords.size());
            Coords shot = computerRemainingFirableCoords.get(shotArrayNum);
            computerRemainingFirableCoords.remove(shotArrayNum);
            System.out.println(shot.getAcross() + ", " + shot.getDown());
            return shot;
        }
        else {
            return null;
        }
    }

}