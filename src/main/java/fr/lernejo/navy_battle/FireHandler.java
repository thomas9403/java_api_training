package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class FireHandler implements HttpHandler {
    private final GameState game;
    private final int MAX_FIRES = 200;
    private int firesExchanged;

    public FireHandler(GameState g) {
        this.game = g;
        this.firesExchanged = 0;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equals("GET")) {
            if (firesExchanged > MAX_FIRES) {
                game.set_game_over(true);
                return;
            }
            String cell = extract_cell(t.getRequestURI().getQuery());
            String[] cellCoordinates = cell.split("");
            int x = cellCoordinates[0].charAt(0) - 'A';
            int y = Integer.parseInt(cellCoordinates[1]) - 1;
            String consequence = this.game.takeFireFromEnemy(x, y);
            boolean shipLeft = this.game.check_ships_left();
            String response = "{\"consequence\": \"" + consequence + "\", \"shipLeft\": " + shipLeft + "}";
            t.getResponseHeaders().set("Content-Type", "application/json");
            Utils.sendResponse(t, 200, response);
            if (shipLeft && !game.is_game_over()) {
                game.set_turn(true);
                firesExchanged++;
                BoardPosition targetCell = intelligentFireSelection();
                String cellAlpha = Utils.translatePosToAlpha(targetCell);
                try {
                    response = String.valueOf(Utils.GetRequest(game.getOpponentAddress() + "/api/game/fire?cell=" + cellAlpha));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.substring(response.indexOf("{")));
                consequence = jsonNode.get("consequence").asText();
                shipLeft = jsonNode.get("shipLeft").asBoolean();
                if (!shipLeft) {
                    game.set_game_over(true);
                } else {
                    game.fireAtCell(targetCell, consequence.equals("hit") || consequence.equals("sunk"));
                }
            }
        } else {
            Utils.sendResponse(t, 404, "Not Found");
        }
    }

    private BoardPosition intelligentFireSelection() {
        double[][] probabilityMap = createProbabilityMap();
        int maxX = 0;
        int maxY = 0;
        double maxProb = 0;
        for (int x = 0; x < probabilityMap.length; x++) {
            for (int y = 0; y < probabilityMap[x].length; y++) {
                if (probabilityMap[x][y] > maxProb) {
                    maxProb = probabilityMap[x][y];
                    maxX = x;
                    maxY = y;
                }
            }
        }
        return new BoardPosition(maxX, maxY);
    }

    private double[][] createProbabilityMap() {
        double[][] probabilityMap = new double[10][10];
        for (int x = 0; x < probabilityMap.length; x++) {
            for (int y = 0; y < probabilityMap[x].length; y++) {
                probabilityMap[x][y] = 0.1;
            }
        }

        // Update probabilities based on current game state
        for (BoardPosition p : game.getHitPositions()) {
            probabilityMap[p.x()][p.y()] = 0;
            // Increase probability of surrounding cells
            for (int x = p.x() - 1; x <= p.x() + 1; x++) {
                for (int y = p.y() - 1; y <= p.y() + 1; y++) {
                    if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                        probabilityMap[x][y] += 0.1;
                    }
                }
            }
        }
        return probabilityMap;
    }

    public static String extract_cell(String query) {
        return Arrays.stream(query.split("&")).filter(p -> p.startsWith("cell="))
            .toList().get(0).split("=")[1];
    }
}
