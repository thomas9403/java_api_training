package fr.lernejo.navy_battle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.Executors;

public class Launcher {
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.out.println("Listen"); return;
        }
        GameState game = new GameState("http://localhost:" + Integer.parseInt(args[0]));
        Server(game);
        if (args.length == 2) Client(game, args);
        while (!game.is_game_over()) {
            if (game.get_turn()) FireProcedure(game);
            try { Thread.sleep(2500); }
            catch (InterruptedException e) { throw new RuntimeException(e); }
        }
        System.out.println("Game is Over!");
    }

    public static BoardPosition RandomPos(GameState game) {
        Random random = new Random();
        int x = 0,y = 0;
        while (!game.getPosState(x, y).equals(Board.State.FREE)){
            x = random.nextInt(10);
            y = random.nextInt(10);
        }
        return new BoardPosition(x, y);
    }

    public static void Client(GameState game, String[] args) {
        try {
            game.set_turn(true); // the client starts the game
            game.setOpponentAddress(args[1]);
            Utils.PostRequest(args[1] + "/api/game/start", "{\"id\":\"1\", \"url\":\"http://localhost:" + args[0] + "\", \"message\":\"May the best code win !\"}");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpServer Server(GameState game) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(game.getPort()), 0);
            server.createContext("/ping", new PingHandler());
            server.createContext("/api/game/start", new StartHandler(game));
            server.createContext("/api/game/fire", new FireHandler(game));
            server.setExecutor(Executors.newSingleThreadExecutor());
            server.start();
            return server;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void FireProcedure(GameState game) {
        try {
            System.out.println("Bonjour a toi !");
            BoardPosition pos = RandomPos(game);
            String cellAlpha = Utils.translatePosToAlpha(pos);
            HttpResponse<String> response = Utils.GetRequest(game.getOpponentAddress() + "/api/game/fire?cell=" + cellAlpha);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());
            String consequence = jsonNode.get("consequence").asText();
            boolean shipLeft = jsonNode.get("shipLeft").asBoolean();
            if (shipLeft) game.set_game_over(true);
            else game.fireAtCell(pos, consequence.equals("hit") || consequence.equals("sunk")).set_turn(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
