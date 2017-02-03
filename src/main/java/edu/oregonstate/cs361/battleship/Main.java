package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {
    public static final int GRID_SIZE = 10;

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

        Ship ship = model.getShipFromID(req.params("id"));
        int row = Integer.parseInt(req.params("row"));
        int col = Integer.parseInt(req.params("col"));
        String orientation = req.params("orientation");

        ship.updatePosition(row, col, orientation);

        return getJSONFromModel(model);
    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
        return null;
    }
}