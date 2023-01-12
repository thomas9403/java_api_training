package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.Arrays;

public class FireHandler implements HttpHandler
{
    private final GameState game;

    public FireHandler(GameState g)
    {
        this.game = g;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equals("GET")) {
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
                Launcher.FireProcedure(game);
            }
        }
        else {
            Utils.sendResponse(t, 404, "Not Found");
        }
    }

    public static String extract_cell(String query)
    {
        return Arrays.stream(query.split("&")).filter(p -> p.startsWith("cell="))
            .toList().get(0).split("=")[1];
    }
}
