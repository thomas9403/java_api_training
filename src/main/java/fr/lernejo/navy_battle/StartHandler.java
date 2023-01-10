package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StartHandler implements HttpHandler
{
    private final GameState game;

    public StartHandler(GameState g) {
        this.game = g;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equals("POST")) {
            String request = stream_to_string(t.getRequestBody());
            JsonNode jsonNode = new ObjectMapper().readTree(request);
            String url = jsonNode.get("url").asText();

            game.newGame().setOpponentAddress(url);

            String body = "{\"id\": \"10\", \"url\": \"" + this.game.getOwnAddress() + "\", \"message\": \"hello\"}";
            Utils.sendResponse(t, 202, body);
        }
        else Utils.sendResponse(t, 404, "Not Found");
    }

    public static String stream_to_string(InputStream s) throws IOException
    {
        InputStreamReader isr = new InputStreamReader(s, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        int b;
        StringBuilder buf = new StringBuilder();
        while ((b = br.read()) != -1){
            buf.append((char) b);
        }
        br.close();
        isr.close();
        return buf.toString();
    }
}
