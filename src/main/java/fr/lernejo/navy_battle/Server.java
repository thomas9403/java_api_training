package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

    public static HttpServer setupServer(GameState game) {
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

}
