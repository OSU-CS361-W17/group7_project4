package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {
        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));
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

    //This function should accept an HTTP request and deseralize it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req){
        Gson gson = new Gson();
        String json = req.body();
        BattleshipModel model = gson.fromJson(json, BattleshipModel.class);
        return model;
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {
        return "SHIP";
    }

    /*
     * Similar to placeShip, but with firing.
     * @param Request req
     * @return String theModel
     */
    private static String fireAt(Request req) {
        int row = Integer.parseInt(req.params("row"));
        int column = Integer.parseInt(req.params("col"));
        Coords targetCoords = new Coords(row, column);

        BattleshipModel theModel = getModelFromReq(req);

        //This method is designated to be shooting AT the computer ships. ("comp" is the target)
        boolean isHit = theModel.updateShot("comp", targetCoords);

        /*
         * Put the AI decision making methods here for WHERE the AI will shoot
         * then take the Coords that the AI decides and set "targetCoords" equal to it.
         */

         targetCoords = new Coords(1,1);

        //This method is designated to be shooting AT the player ships. ("player" is the target)
        boolean isHitAI = theModel.updateShot("player", targetCoords);

        return getJSONFromModel(theModel);
    }
}