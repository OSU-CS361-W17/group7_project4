package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {

        // Makes Spark log unhandled exceptions
        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });

        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to scan
        post("/scan/:row/:col", (req, res) -> scan(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:name/:row/:col/:orientation", (req, res) -> placeShip(req));
    }

    //This function should return a new model
    static String newModel() {
        BattleshipModel model = new BattleshipModel();
        return getJSONFromModel(model);
    }

    //Convert a BattleshipModel to a JSON string
    private static String getJSONFromModel(BattleshipModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        return json;
    }

    //This function should accept an HTTP request and deserialize it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req){
        Gson gson = new Gson();
        String json = req.body();
        BattleshipModel model = gson.fromJson(json, BattleshipModel.class);
        return model;
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {
        BattleshipModel model = getModelFromReq(req);
        if (model == null)
            model = new BattleshipModel();

        String name = req.params("name");
        int row = Integer.parseInt(req.params("row"));
        int col = Integer.parseInt(req.params("col"));
        String orientation = req.params("orientation");

        model.updateShipPosition("player", name, row, col, orientation);

        return getJSONFromModel(model);
    }

    /*
     * Similar to placeShip, but with firing.
     * @param Request req
     * @return String theModel
     */
    private static String fireAt(Request req) {
        int row = Integer.parseInt(req.params("row"));
        int column = Integer.parseInt(req.params("col"));
        Coords targetCoords = new Coords(column, row);

        BattleshipModel theModel = getModelFromReq(req);
        if (theModel == null)
            theModel = new BattleshipModel();

        //This method is designated to be shooting AT the computer ships. ("comp" is the target)
        theModel.updateShot("computer", targetCoords);

        /*
         * Put the AI decision making methods here for WHERE the AI will shoot
         * then take the Coords that the AI decides and set "targetCoords" equal to it.
         */
        targetCoords = theModel.getComputerFireCoords();

        //This method is designated to be shooting AT the player ships. ("player" is the target)
        theModel.updateShot("player", targetCoords);

        return getJSONFromModel(theModel);
    }

    /* Scan for enemy ships at row and column specified in request, then have the AI shoot at the player
     * @param req the POST request body with parameters "row" and "col"
     * @return Returns the BattleshipModel after scanning & firing in the form of a JSON string
     */
    private static String scan(Request req) {
        BattleshipModel model = getModelFromReq(req);
        int row = Integer.parseInt(req.params("row"));
        int column = Integer.parseInt(req.params("col"));

        model.scan(row, column);

        // Fire at player
        Coords target = model.getComputerFireCoords();
        model.updateShot("player", target);

        return getJSONFromModel(model);
    }
}