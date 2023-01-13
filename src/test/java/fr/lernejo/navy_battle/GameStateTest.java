package fr.lernejo.navy_battle;
import java.net.InetSocketAddress;
import java.net.URI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void test_getPosState(){
        GameState game = new GameState("http://localhost:8080");
        Assertions.assertThat(game.getPosState(0,0)).isEqualTo(Board.State.FREE);
    }

    @Test
    void test_getPort(){
        GameState game = new GameState("http://localhost:8080");
        Assertions.assertThat(game.getPort()).isEqualTo(8080);
    }

    @Test
    void test_setOpponentAddress(){
        GameState game = new GameState("http://localhost:8080");
        game.setOpponentAddress("http://localhost:8080");
        Assertions.assertThat(game.getOpponentAddress()).isEqualTo("http://localhost:8080");
    }

    @Test
    void test_takeFireFromEnemy(){
        GameState game = new GameState("http://localhost:8080");
        Assertions.assertThat(game.takeFireFromEnemy(0,0)).isEqualTo("hit");
    }

    @Test
    void test_is_game_over(){
        GameState game = new GameState("http://localhost:8080");
        Assertions.assertThat(game.is_game_over()).isEqualTo(false);
    }

}
