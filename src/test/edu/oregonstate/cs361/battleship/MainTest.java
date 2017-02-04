package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;

class MainTest {

    @BeforeAll
    public static void beforeClass() {
        Main.main(null);
        awaitInitialization();
    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testGetModel() {
        TestResponse res = request("GET", "/model");
        assertEquals(200, res.status);
        // Blank model with all ships defined at (0,0) and no hits or misses registered
        // Should be the same as the model specified in project's README.md so it's compatible with the view
        assertEquals("{\"playerShips\":[{\"name\":\"aircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},{\"name\":\"battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},{\"name\":\"cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},{\"name\":\"destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},{\"name\":\"submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false}],\"aircraftCarrier\":{\"name\":\"aircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"battleship\":{\"name\":\"battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"cruiser\":{\"name\":\"cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"destroyer\":{\"name\":\"destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"submarine\":{\"name\":\"submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"compShips\":[{\"name\":\"computer_aircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},{\"name\":\"computer_battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},{\"name\":\"computer_cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},{\"name\":\"computer_destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},{\"name\":\"computer_submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false}],\"computer_aircraftCarrier\":{\"name\":\"computer_aircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"computer_battleship\":{\"name\":\"computer_battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"computer_cruiser\":{\"name\":\"computer_cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"computer_destroyer\":{\"name\":\"computer_destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"computer_submarine\":{\"name\":\"computer_submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"isVert\":false},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[],\"computerRemainingFirableCoords\":[{\"Across\":1,\"Down\":1},{\"Across\":1,\"Down\":2},{\"Across\":1,\"Down\":3},{\"Across\":1,\"Down\":4},{\"Across\":1,\"Down\":5},{\"Across\":1,\"Down\":6},{\"Across\":1,\"Down\":7},{\"Across\":1,\"Down\":8},{\"Across\":1,\"Down\":9},{\"Across\":1,\"Down\":10},{\"Across\":2,\"Down\":1},{\"Across\":2,\"Down\":2},{\"Across\":2,\"Down\":3},{\"Across\":2,\"Down\":4},{\"Across\":2,\"Down\":5},{\"Across\":2,\"Down\":6},{\"Across\":2,\"Down\":7},{\"Across\":2,\"Down\":8},{\"Across\":2,\"Down\":9},{\"Across\":2,\"Down\":10},{\"Across\":3,\"Down\":1},{\"Across\":3,\"Down\":2},{\"Across\":3,\"Down\":3},{\"Across\":3,\"Down\":4},{\"Across\":3,\"Down\":5},{\"Across\":3,\"Down\":6},{\"Across\":3,\"Down\":7},{\"Across\":3,\"Down\":8},{\"Across\":3,\"Down\":9},{\"Across\":3,\"Down\":10},{\"Across\":4,\"Down\":1},{\"Across\":4,\"Down\":2},{\"Across\":4,\"Down\":3},{\"Across\":4,\"Down\":4},{\"Across\":4,\"Down\":5},{\"Across\":4,\"Down\":6},{\"Across\":4,\"Down\":7},{\"Across\":4,\"Down\":8},{\"Across\":4,\"Down\":9},{\"Across\":4,\"Down\":10},{\"Across\":5,\"Down\":1},{\"Across\":5,\"Down\":2},{\"Across\":5,\"Down\":3},{\"Across\":5,\"Down\":4},{\"Across\":5,\"Down\":5},{\"Across\":5,\"Down\":6},{\"Across\":5,\"Down\":7},{\"Across\":5,\"Down\":8},{\"Across\":5,\"Down\":9},{\"Across\":5,\"Down\":10},{\"Across\":6,\"Down\":1},{\"Across\":6,\"Down\":2},{\"Across\":6,\"Down\":3},{\"Across\":6,\"Down\":4},{\"Across\":6,\"Down\":5},{\"Across\":6,\"Down\":6},{\"Across\":6,\"Down\":7},{\"Across\":6,\"Down\":8},{\"Across\":6,\"Down\":9},{\"Across\":6,\"Down\":10},{\"Across\":7,\"Down\":1},{\"Across\":7,\"Down\":2},{\"Across\":7,\"Down\":3},{\"Across\":7,\"Down\":4},{\"Across\":7,\"Down\":5},{\"Across\":7,\"Down\":6},{\"Across\":7,\"Down\":7},{\"Across\":7,\"Down\":8},{\"Across\":7,\"Down\":9},{\"Across\":7,\"Down\":10},{\"Across\":8,\"Down\":1},{\"Across\":8,\"Down\":2},{\"Across\":8,\"Down\":3},{\"Across\":8,\"Down\":4},{\"Across\":8,\"Down\":5},{\"Across\":8,\"Down\":6},{\"Across\":8,\"Down\":7},{\"Across\":8,\"Down\":8},{\"Across\":8,\"Down\":9},{\"Across\":8,\"Down\":10},{\"Across\":9,\"Down\":1},{\"Across\":9,\"Down\":2},{\"Across\":9,\"Down\":3},{\"Across\":9,\"Down\":4},{\"Across\":9,\"Down\":5},{\"Across\":9,\"Down\":6},{\"Across\":9,\"Down\":7},{\"Across\":9,\"Down\":8},{\"Across\":9,\"Down\":9},{\"Across\":9,\"Down\":10},{\"Across\":10,\"Down\":1},{\"Across\":10,\"Down\":2},{\"Across\":10,\"Down\":3},{\"Across\":10,\"Down\":4},{\"Across\":10,\"Down\":5},{\"Across\":10,\"Down\":6},{\"Across\":10,\"Down\":7},{\"Across\":10,\"Down\":8},{\"Across\":10,\"Down\":9},{\"Across\":10,\"Down\":10}]}",res.body);
    }

    @Test
    public void testPlaceShip() {
        TestResponse res = request("POST", "/placeShip/aircraftCarrier/2/4/horizontal");
        assertEquals(200, res.status);

        Gson gson = new Gson();
        String json = res.body;
        BattleshipModel model = gson.fromJson(json, BattleshipModel.class);
        Ship aircraftCarrier = model.getShipFromName("aircraftCarrier");

        int startDown = aircraftCarrier.getStart().getDown();
        int startAcross = aircraftCarrier.getStart().getAcross();
        int endDown = aircraftCarrier.getEnd().getDown();
        int endAcross = aircraftCarrier.getEnd().getAcross();
        int length = aircraftCarrier.getLength();

        assertEquals(2, startDown);
        assertEquals(4, startAcross);
        assertEquals(2, endDown);
        assertEquals(4 + length - 1, endAcross);
    }

    @Test
    public void fireAt() {
        TestResponse res = request("POST", "/fire/1/1");
        assertEquals(200, res.status);
        assertNotNull(res.body);
    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }


}