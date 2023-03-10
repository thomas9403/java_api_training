package fr.lernejo.navy_battle;
import java.io.IOException;
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

class LauncherTest {
    @Test
    void wrong_arg(){
        assertThrows(NumberFormatException.class, ()->{
            Launcher.main(new String[]{"azert"});
        });
    }

    @Test
    void no_arg(){
        assertThrows(NumberFormatException.class, ()->{
            Launcher.main(new String[]{""});
        });
    }
    @Test
    void test_gamestate(){
        GameState game = new GameState("http://localhost:8080");
        Assertions.assertThat(game.getPort()).isEqualTo(8080);
    }

    @Test
    //tester Server(game);
    void test_server(){
        GameState game = new GameState("http://localhost:8080");
        Assertions.assertThat(Launcher.Server(game)).isNotNull();
    }

    @Test
    //tester FireProcedure(game);
    void test_fireprocedure(){
        GameState game = new GameState("http://localhost:8080");
        Launcher.Client(game, new String[]{"8080", "http://localhost:8081"});
        Launcher.FireProcedure(game);
        Assertions.assertThat(game.getPosState(0,0)).isEqualTo(Board.State.FREE);
    }

    @Test
    //tester RandomPos(game);
    void test_randompos(){
        GameState game = new GameState("http://localhost:8080");
        Assertions.assertThat(Launcher.RandomPos(game)).isNotNull();
    }

    @Test
    //tester mapper
    void test_mapper() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree("{\"consequence\":\"MISS\", \"shipLeft\":true}");
        Assertions.assertThat(jsonNode.get("consequence").asText()).isEqualTo("MISS");
        Assertions.assertThat(jsonNode.get("shipLeft").asBoolean()).isEqualTo(true);
    }

}
