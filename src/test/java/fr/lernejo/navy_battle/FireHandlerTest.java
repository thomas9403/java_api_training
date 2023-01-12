package fr.lernejo.navy_battle;
import java.io.IOException;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FireHandlerTest {

    //public void handle(HttpExchange t) throws IOException {
        //if (t.getRequestMethod().equals("GET")) {
            //String cell = extract_cell(t.getRequestURI().getQuery());
            //String[] cellCoordinates = cell.split("");
            //int x = cellCoordinates[0].charAt(0) - 'A';
            //int y = Integer.parseInt(cellCoordinates[1]) - 1;
            //String consequence = this.game.takeFireFromEnemy(x, y);
            //boolean shipLeft = this.game.check_ships_left();
            //String response = "{\"consequence\": \"" + consequence + "\", \"shipLeft\": " + shipLeft + "}";
            //t.getResponseHeaders().set("Content-Type", "application/json");
            //Utils.sendResponse(t, 200, response);
            //if (shipLeft && !game.is_game_over()) {
            //    game.set_turn(true);
          //      Launcher.FireProcedure(game);
        //    }
      //  } else {Utils.sendResponse(t, 404, "Not Found");}
    //}

    //faire des tests sur cette fonction

    @Test
    void extract_cell(){
        String query = "cell=A1";
        String cell = FireHandler.extract_cell(query);
        Assertions.assertThat(cell).isEqualTo("A1");
    }

}
