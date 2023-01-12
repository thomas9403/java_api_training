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

}
