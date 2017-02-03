package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;

public class BattleshipModel {

    // Player's ships
    private Ship aircraftCarrier;
    private Ship battleship;
    private Ship cruiser;
    private Ship destroyer;
    private Ship submarine;

    // AI's ships
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

        computer_aircraftCarrier = new Ship("Computer_AircraftCarrier", 5);
        computer_battleship = new Ship("Computer_Battleship", 4);
        computer_cruiser = new Ship("Computer_Cruiser", 3);
        computer_destroyer = new Ship("Computer_Destroyer", 2);
        computer_submarine = new Ship("Computer_Submarine", 2);

        playerHits = new ArrayList<Coords>();
        playerMisses = new ArrayList<Coords>();
        computerHits = new ArrayList<Coords>();
        computerMisses = new ArrayList<Coords>();
    }

    // Makes it possible to retrieve ships from strings of their name
    // Checking for these fields using Reflection might be more elegant (if we want to change the ships in the game)...
    // but that's a future consideration, and a PITA
    public Ship getShipFromID(String shipID) {
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
}