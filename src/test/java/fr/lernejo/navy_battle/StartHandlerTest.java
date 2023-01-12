package fr.lernejo.navy_battle;

import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class StartHandlerTest {

    @Test
    void start_handler_test() throws Exception {
        var server = Launcher.Server(new GameState("http://localhost:1234"));
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(URI.create("http://localhost:1234/api/game/start")).POST(HttpRequest.BodyPublishers.ofString("{\"url\": \"http://localhost:1234\"}")).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(response.statusCode()).isEqualTo(202);
        Assertions.assertThat(response.body()).isEqualTo("{\"id\": \"10\", \"url\": \"http://localhost:1234\", \"message\": \"May the best code win !\"}");
        server.stop(0);
    }

    //faire un autre test mais erreur 404
    void start_handler_test_404() throws Exception {
        var server = Launcher.Server(new GameState("http://localhost:1234"));
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(URI.create("http://localhost:1234/api/game/start")).POST(HttpRequest.BodyPublishers.ofString("{\"url\": \"http://localhost:1234\"}")).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(response.statusCode()).isEqualTo(404);
        Assertions.assertThat(response.body()).isEqualTo("{\"id\": \"10\", \"url\": \"http://localhost:1234\", \"message\": \"May the best code win !\"}");
        server.stop(0);
    }

}
