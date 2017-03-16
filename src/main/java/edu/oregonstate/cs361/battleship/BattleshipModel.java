package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.Random;
import edu.oregonstate.cs361.battleship.Coords.Direction;

public class BattleshipModel {


    // Declares a Constant for size of the game grid.
    public static final int GRID_SIZE = 10;

    // Player's ships
    private MilitaryShip aircraftCarrier;
    private MilitaryShip battleship;
    private Ship clipper;
    private Ship dinghy;
    private MilitaryShip submarine;

    // AI's ships
    private MilitaryShip computer_aircraftCarrier;
    private MilitaryShip computer_battleship;
    private Ship computer_clipper;
    private Ship computer_dinghy;
    private MilitaryShip computer_submarine;

    // Shots fired
    private ArrayList<Coords> playerHits; // Hits on the player's grid
    private ArrayList<Coords> playerMisses;
    private ArrayList<Coords> computerHits; // Hits on the computer's grid
    private ArrayList<Coords> computerMisses;

    private ArrayList<Coords> computerRemainingFirableCoords; // Tracks coordinates where the AI hasn't shot yet
    private Coords targetHit; // location where the AI initially hits a ship that it will try to sink in hard mode
    private int tracker = 0;

    private boolean scanResult = false; // True when the most recent scan detected a ship
    private boolean gameOver = false;   // True when all of the player or computer's ships are sunk

    private boolean easyMode; // True for easy mode, false for hard mode


    public BattleshipModel() {
        this(false); // Default to hard mode
    }

    public BattleshipModel(boolean easyMode) {
        aircraftCarrier = new MilitaryShip("aircraftCarrier", 5, false);
        battleship = new MilitaryShip("battleship", 4, true);
        clipper = new Ship("clipper", 3);
        dinghy = new Ship("dinghy", 1);
        submarine = new MilitaryShip("submarine", 2, true);

        computer_aircraftCarrier = new MilitaryShip("computer_aircraftCarrier", 5, false);
        computer_battleship = new MilitaryShip("computer_battleship", 4, true);
        computer_clipper = new Ship("computer_clipper", 3);
        computer_dinghy = new Ship("computer_dinghy", 1);
        computer_submarine = new MilitaryShip("computer_submarine", 2, true);

        playerHits = new ArrayList<Coords>();
        playerMisses = new ArrayList<Coords>();
        computerHits = new ArrayList<Coords>();
        computerMisses = new ArrayList<Coords>();

        //Calls for clean new AI fireable array.
        setCleanComputerShotArray();

        this.easyMode = easyMode;

        if (easyMode) {
            // Place ships statically in easy mode
            updateShipPosition("computer", "computer_aircraftCarrier", 1, 1, "horizontal");
            updateShipPosition("computer", "computer_battleship", 2, 1, "horizontal");
            updateShipPosition("computer", "computer_clipper", 3, 1, "horizontal");
            updateShipPosition("computer", "computer_dinghy", 4, 1, "horizontal");
            updateShipPosition("computer", "computer_submarine", 5, 1, "vertical");
        } else {
            // Place ships randomly in hard mode
            placeAllAI();
        }
    }

    /*
    Is used to create a new, clean shot array for the
    AI to reference on making shot decisions.
     */
    public void setCleanComputerShotArray() {

        //Sets the shot array's size based on GRID_SIZE.
        computerRemainingFirableCoords = new ArrayList<Coords>(GRID_SIZE * GRID_SIZE);

        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++) {
                computerRemainingFirableCoords.add(new Coords(j+1, i+1));
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


    /*
     * Check if any of a target player or computer's ships overlap with given coordinates
     * @param target specifies who's ships to check for, either "player" or "computer"
     * @param coordinates the position to check for ships at
     * @return true if ship found at coordinates, false otherwise
     */
    public Ship checkShipCollisions(String target, Coords coordinates) {
        Ship shipFound = null;

        if (target.equals("player")) {
            for (Ship ship : getPlayerShips()) {
                if (ship.checkCollision(coordinates)) {
                    shipFound = ship;
                    break;
                }
            }
        } else if (target.equals("computer")) {
            for (Ship ship : getComputerShips()) {
                if (ship.checkCollision(coordinates)) {
                    shipFound = ship;
                    break;
                }
            }
        }

        return shipFound;
    }

    /*
     * Places a shot against the player or computer. Updates the appropriate ArrayList
     * depending on the outcome (hit or miss) of the shot.
     * @param targetSide specifies which side to shoot at, either "player" or "computer"
     * @param targetArea the coordinates of the shot being placed
     * @return true if the shot was a hit, false if it was a miss
     */
    public boolean updateShot(String targetSide, Coords targetArea) {
        boolean collision = false;

        if (targetArea == null)
            return false;

        //find ship at fire location
        Ship hitShip = checkShipCollisions(targetSide, targetArea);

        //if firing at computer
        if (targetSide.equals("computer")) {
            //and input is valid
            if (hitShip != null) {
                computerHits.add(targetArea);
                if (hitShip.addHit()) // if the hit ship is sunk
                    checkGameOver("computer");
                collision = true;
            } else
                computerMisses.add(targetArea);

        } else if (targetSide.equals("player")) {
            if (hitShip != null) {
                // if no ship is being targeted, remember it for intelligent firing in hard difficulty
                if (targetHit == null)
                    targetHit = targetArea;

                playerHits.add(targetArea);
                if (hitShip.addHit()) { // if the hit ship is sunk
                    checkGameOver("player");

                    // if the target ship is the one that was sunk, go back to random firing (hard difficulty)
                    if (hitShip.checkCollision(targetHit))
                        targetHit = null;

                    // if the sunk ship was Civilian, record hits for all of it's tiles
                    // this is necessary for the AI's intelligent firing in hard difficulty
                    if (hitShip.getClass().equals(Ship.class)) // this feels wrong
                        hitEntireShip(hitShip, targetSide);
                }

                collision = true;

            } else
                playerMisses.add(targetArea);
        } else
            throw new IllegalArgumentException("targetArea must be either \"player\" or \"computer\"");

        return collision;
    }

    /* Update the position of a ship specified by name
     * @param whichShips specifies which team's ship is being placed, either "player" or "computer"
     * @param name the name of the ship to move
     * @param row the row number to move the ship to (down)
     * @param column the column number to move the ship to (across)
     * @param orientation either "horizontal" or "vertical" indicating which direction the ship extends
     * @return true if ship placed successfully, false otherwise
     */
    public boolean updateShipPosition(String whichShips, String name, int row, int column, String orientation) {
        Ship ship = getShipFromName(name);

        for (int i = 0; i < ship.getLength(); i++) {
            if (orientation.equals("vertical")) {
                Ship collision = checkShipCollisions(whichShips, new Coords(row + i, column));
                if (collision != null && !collision.getName().equals(name))
                    return false;

            } else if (orientation.equals("horizontal")) {
                Ship collision = checkShipCollisions(whichShips, new Coords(row, column + i));
                if (collision != null && !collision.getName().equals(name))
                    return false;
            }
        }

        return ship.updatePosition(row, column, orientation);
    }

    // Makes it possible to retrieve ships from strings of their name
    public Ship getShipFromName(String name) {
        Ship ship = null;
        switch (name) {
            case "aircraftCarrier": ship = aircraftCarrier; break;
            case "battleship": ship = battleship; break;
            case "clipper": ship = clipper; break;
            case "dinghy": ship = dinghy; break;
            case "submarine": ship = submarine; break;
            case "computer_aircraftCarrier": ship = computer_aircraftCarrier; break;
            case "computer_battleship": ship = computer_battleship; break;
            case "computer_clipper": ship = computer_clipper; break;
            case "computer_dinghy": ship = computer_dinghy; break;
            case "computer_submarine": ship = computer_submarine; break;
        }
        return ship;
    }


    /* Determines where the AI will fire its next shot.
    @return Coords specifying the location to shoot.
    */
    public Coords getComputerFireCoords() {
        if (!easyMode) // Hard mode
        {
            if (targetHit == null) // No ship is currently being targeted
            {
                // Fire randomly where the computer hasn't yet
                if (computerRemainingFirableCoords.size() != 0) {
                    Random randNum = new Random();
                    int shotArrayNum = randNum.nextInt(computerRemainingFirableCoords.size());
                    Coords shot = computerRemainingFirableCoords.get(shotArrayNum);
                    computerRemainingFirableCoords.remove(shotArrayNum);
                    return shot;
                } else {
                    gameOver = true; // End the game, there's nowhere left to shoot
                    return null;
                }
            }
            else // Continue shooting at the same ship until it's sunk
            {
                // Start by looking at adjacent coordinates of targetHit
                for (Direction direction : Direction.values())
                {
                    Coords nextTarget = targetHit.getInDirection(direction);

                    // If there's a hit in this direction, follow it until there's a spot we can shoot at
                    while (playerHits.contains(nextTarget)) {
                        nextTarget = nextTarget.getInDirection(direction); // Check one further in this direction
                        if (!nextTarget.onGrid()) // We stop caring if it's off-grid
                            break;

                        // If the next location is blank (not a hit or a miss), fire at it
                        if (!playerHits.contains(nextTarget) && !playerMisses.contains(nextTarget))
                            return nextTarget;
                    }
                }

                // There are no adjacent hits to extend on, so just pick an adjacent blank Coord
                for (Coords coords : targetHit.getAdjacents()) {
                    if (!playerHits.contains(coords) && !playerMisses.contains(coords) && coords.onGrid())
                        return coords;
                }
            }
        } else // Easy mode
        {

            // Fire randomly where the computer hasn't yet
            if (computerRemainingFirableCoords.size() != 0) {

                Coords shot = computerRemainingFirableCoords.get(tracker);
                computerRemainingFirableCoords.remove(tracker);
                return shot;
            } else {
                gameOver = true; // End the game, there's nowhere left to shoot
                return null;
            }

        }
        return null;
    }

    public void placeAIShip(String name) {
        String orient;
        int dir;
        boolean worked;
        Random randNum = new Random();
        do {
            int x = (randNum.nextInt(GRID_SIZE) + 1);
            int y = (randNum.nextInt(GRID_SIZE) + 1);

            dir = (randNum.nextInt(2));

            if (dir == 0) orient = "horizontal";
            else if (dir == 1) orient = "vertical";
            else orient = "error orientation";

            worked = updateShipPosition("computer", name, x, y, orient);

        } while (worked == false);
    }

    public void placeAllAI() {
        placeAIShip("computer_aircraftCarrier");
        placeAIShip("computer_dinghy");
        placeAIShip("computer_submarine");
        placeAIShip("computer_battleship");
        placeAIShip("computer_clipper");
    }

    /* Perform a scan and update scanResult if one of the computer's ships falls into the plus region
     * where the specified coordinates is the center of the plus
     * @param row the row (down) of the center of the scan
     * @param column the column (across) of the center of the scan
     */
    public void scan(int row, int column) {
        Coords coord = new Coords(row, column);
        scanResult = false;

        for (Ship ship : getComputerShips()) {
            if (ship.scan(coord))
                scanResult = true;
        }
    }

    /* Get the scanResult boolean (for after a scan has been performed)
     * @return true if scan found an enemy ship, false otherwise
     */
    public boolean getScanResult() {
        return scanResult;
    }

    /* Helper that returns an array of the player's ships
     * @return An array of all of the player's ships in BattleshipModel
     */
    private Ship[] getPlayerShips() {
        Ship[] playerShips = new Ship[5];
        playerShips[0] = aircraftCarrier;
        playerShips[1] = battleship;
        playerShips[2] = clipper;
        playerShips[3] = dinghy;
        playerShips[4] = submarine;
        return playerShips;
    }

    /* Helper that returns an array of the computer's ships
     * @return An array of all of the computer's ships in BattleshipModel
     */
    private Ship[] getComputerShips() {
        Ship[] computerShips = new Ship[5];
        computerShips[0] = computer_aircraftCarrier;
        computerShips[1] = computer_battleship;
        computerShips[2] = computer_clipper;
        computerShips[3] = computer_dinghy;
        computerShips[4] = computer_submarine;
        return computerShips;
    }

    /* Checks conditions relating to game end and updates gameOver status as needed.
     * The game is over when all of the player or computer's ships are sunk.
     * @param targetArea Either "player" or "computer" specifying which side's ships to check
     */
    private void checkGameOver(String targetArea) {
        switch (targetArea) {
            case "player":
                for (Ship ship : getPlayerShips()) {
                    if (!ship.checkSunk())
                        return;
                }
                break;

            case "computer":
                for (Ship ship : getComputerShips()) {
                    if (!ship.checkSunk())
                        return;
                }
                break;

            default: throw new IllegalArgumentException("targetArea must be either \"player\" or \"computer\"");
        }

        gameOver = true;
    }

    /* Takes a ship and a target grid and adds hits for every tile the ship overlaps with.
     * @param ship The Ship to add hits for
     * @param targetSide Either "player" or "computer", specifying which side to apply hits for
     */
    private void hitEntireShip(Ship ship, String targetSide) {
        Coords[] shipCoords = ship.getCoordsArray();
        for (Coords coords : shipCoords) {
            if (targetSide.equals("player") && !playerHits.contains(coords))
                playerHits.add(coords);
            if (targetSide.equals("computer") && !computerHits.contains(coords))
                computerHits.add(coords);
        }
    }
}