package com.example.dragonsofmugloar.config;

import com.example.dragonsofmugloar.models.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public Game startGame() {
        return new RestTemplate().postForObject("https://dragonsofmugloar.com/api/v2/game/start", null, Game.class);
    }
}
