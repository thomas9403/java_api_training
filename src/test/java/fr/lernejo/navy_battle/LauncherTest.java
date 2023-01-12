package fr.lernejo.navy_battle;
import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    //vérifier que le serveur est bien lancé
    @Test
    void server_is_running() throws Exception {
        var server = Launcher.Server(new GameState("http://localhost:1234"));
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(URI.create("http://localhost:1234/ping")).GET().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.body()).isEqualTo("OK");
        server.stop(0);
    }

    //vérifie si args.length == 2 et si oui, Client(game, args) est appelé
    @Test
    void client_is_called() throws Exception {
        var server = Launcher.Server(new GameState("http://localhost:1234"));
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(URI.create("http://localhost:1234/api/game/start")).POST(HttpRequest.BodyPublishers.ofString("{\"url\": \"http://localhost:1234\"}")).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(response.statusCode()).isEqualTo(202);
        Assertions.assertThat(response.body()).isEqualTo("{\"id\": \"10\", \"url\": \"http://localhost:1234\", \"message\": \"May the best code win !\"}");
        server.stop(0);
    }

}
