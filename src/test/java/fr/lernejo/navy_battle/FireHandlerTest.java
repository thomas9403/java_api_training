package fr.lernejo.navy_battle;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class FireHandlerTest {

    @Test
    void extract_cell(){
        String query = "cell=A1";
        String cell = FireHandler.extract_cell(query);
        Assertions.assertThat(cell).isEqualTo("A1");
    }

    @Test
    void verify_response(){
        GameState game = new GameState("http://localhost:1234");
        game.set_turn(true);
        game.set_game_over(false);
        String cell = "A1";
        String[] cellCoordinates = cell.split("");
        int x = cellCoordinates[0].charAt(0) - 'A';
        int y = Integer.parseInt(cellCoordinates[1]) - 1;
        String consequence = game.takeFireFromEnemy(x, y);
        boolean shipLeft = game.check_ships_left();
        String response = "{\"consequence\": \"" + consequence + "\", \"shipLeft\": " + shipLeft + "}";
        Assertions.assertThat(response).isEqualTo("{\"consequence\": \"hit\", \"shipLeft\": true}");
    }

    @Test
    void check_ships_left(){
        GameState game = new GameState("http://localhost:1234");
        game.set_turn(true);
        game.set_game_over(false);
        String cell = "A1";
        String[] cellCoordinates = cell.split("");
        int x = cellCoordinates[0].charAt(0) - 'A';
        int y = Integer.parseInt(cellCoordinates[1]) - 1;
        String consequence = game.takeFireFromEnemy(x, y);
        boolean shipLeft = game.check_ships_left();
        Assertions.assertThat(shipLeft).isEqualTo(true);
    }

    @Test
    void takeFireFromEnemy(){
        GameState game = new GameState("http://localhost:1234");
        game.set_turn(true);
        game.set_game_over(false);
        String cell = "A1";
        String[] cellCoordinates = cell.split("");
        int x = cellCoordinates[0].charAt(0) - 'A';
        int y = Integer.parseInt(cellCoordinates[1]) - 1;
        String consequence = game.takeFireFromEnemy(x, y);
        Assertions.assertThat(consequence).isEqualTo("hit");
    }

    @Test
    void test_cellcoordinates(){
        String cell = "A1";
        String[] cellCoordinates = cell.split("");
        int x = cellCoordinates[0].charAt(0) - 'A';
        int y = Integer.parseInt(cellCoordinates[1]) - 1;
        Assertions.assertThat(x).isEqualTo(0);
        Assertions.assertThat(y).isEqualTo(0);
    }

    @Test
    public void testFireHandler() throws IOException, InterruptedException {
        // Initialize game state
        GameState game = new GameState("localhost:8000");
        game.newGame();

        // Start a simple HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/api/game/fire", new FireHandler(game));
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();

        // Send a GET request to the server
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8000/api/game/fire?cell=A1"))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verify that the response is as expected
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.body());
        Assertions.assertThat(jsonNode.get("consequence").asText()).isEqualTo("hit");
        Assertions.assertThat(jsonNode.get("shipLeft").asBoolean()).isEqualTo(true);

        // Stop the server
        server.stop(0);
    }

}
