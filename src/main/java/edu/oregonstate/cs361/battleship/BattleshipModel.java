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

        //Initialize AI ships on coords
        //uncomment this function after placement method implemented
        //placeAllAI();

        updateShipPosition("computer_aircraftCarrier", 2, 2, "horizontal");
        updateShipPosition("computer_battleship", 3, 8, "vertical");
        updateShipPosition("computer_cruiser", 1, 6, "vertical");
        updateShipPosition("computer_destroyer", 9, 9, "horizontal");
        updateShipPosition("computer_submarine", 5, 5, "horizontal");

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
        Random randNum = new Random();
        int across = (randNum.nextInt(GRID_SIZE) + 1);
        int down = (randNum.nextInt(GRID_SIZE) + 1);

        return new Coords(across, down);
    }

    public void placeAIShip(String name){
        String orient;
        int dir;
        boolean fail;
        Random randNum = new Random();


        do {
            int x = (randNum.nextInt(GRID_SIZE) + 1);
            int y = (randNum.nextInt(GRID_SIZE) + 1);

            dir = (randNum.nextInt(1));

            if (dir == 0) orient = "horizontal";
            else if (dir == 1) orient = "vertical";
            else orient = "error orientation";

            //INCLUDE FUNCTION TO FIND OUT IF OVERLAP OR OUT OF BOUNDS
            //if (not overlapping or outside of grid)
            updateShipPosition(name, 2, 2, orient);
            fail = false;
            //else
            // fail = true;
        }while(fail == true);


    }

    public void placeAllAI(){

                placeAIShip("computer_aircraftCarrier");
                placeAIShip("computer_destroyer");
                placeAIShip("computer_submarine");
                placeAIShip("computer_battleship");
                placeAIShip("computer_cruiser");


    }

}