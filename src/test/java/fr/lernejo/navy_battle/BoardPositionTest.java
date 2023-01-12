package fr.lernejo.navy_battle;
import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BoardPositionTest {

    @Test
    void random_pos(){
        GameState game = new GameState("http://localhost:1234");
        BoardPosition pos = Launcher.RandomPos(game);
        Assertions.assertThat(pos.getX()).isBetween(0, 9);
        Assertions.assertThat(pos.getY()).isBetween(0, 9);
    }

    @Test
    void to_string(){
        BoardPosition pos = new BoardPosition(1, 2);
        Assertions.assertThat(pos.toString()).isEqualTo("(1,2)");
    }

    @Test
    void equals(){
        BoardPosition pos = new BoardPosition(1, 2);
        BoardPosition pos2 = new BoardPosition(1, 2);
        Assertions.assertThat(pos.equals(pos2)).isTrue();
    }

}
