package fr.lernejo.navy_battle;

import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class PingHandlerTest {

    @Test
    void ping_handler_test() throws Exception {
        PingHandler ping = new PingHandler();
        var server = Launcher.Server(new GameState("http://localhost:1234"));
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(URI.create("http://localhost:1234/ping")).GET().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.body()).isEqualTo("OK");
        server.stop(0);
    }

}
