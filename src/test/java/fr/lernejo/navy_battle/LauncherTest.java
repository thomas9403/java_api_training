package fr.lernejo.navy_battle;
import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

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


    //public static void main(String[] args) {
        //if (args.length == 0 || args.length > 2) {
          //  System.out.println("Listen"); return;
        //}
        //GameState game = new GameState("http://localhost:" + Integer.parseInt(args[0]));
        //Server(game);
        //if (args.length == 2) Client(game, args);
        //while (!game.is_game_over()) {
            //if (game.get_turn()) FireProcedure(game);
           // try { Thread.sleep(2500); }
         // }
     //   System.out.println("Game is Over!");
   // }

    @Test
    //tester GameState game = new GameState("http://localhost:" + Integer.parseInt(args[0]));
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

}
