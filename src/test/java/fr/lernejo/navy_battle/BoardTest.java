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

class BoardTest {
    @Test
    public void testTakeFireFromEnemy() {
        Board board = new Board();

        // Test a miss
        Board.FireResult result = board.takeFireFromEnemy(new BoardPosition(5,5));
        assertEquals(Board.FireResult.HIT, result);
        assertEquals(Board.State.FREE, board.getCellState(5,5));
        assertEquals(true, board.shipLeft());

        // Test a hit
        result = board.takeFireFromEnemy(new BoardPosition(0,0));
        assertEquals(Board.FireResult.HIT, result);
        assertEquals(Board.State.FREE, board.getCellState(0,0));
        assertEquals(true, board.shipLeft());

        // Test a sunk ship
        board.takeFireFromEnemy(new BoardPosition(0,1));
        board.takeFireFromEnemy(new BoardPosition(0,2));
        board.takeFireFromEnemy(new BoardPosition(0,3));
        board.takeFireFromEnemy(new BoardPosition(0,4));
        result = board.takeFireFromEnemy(new BoardPosition(0,5));
        assertEquals(Board.FireResult.MISS, result);
        assertEquals(true, board.shipLeft());
    }
}

