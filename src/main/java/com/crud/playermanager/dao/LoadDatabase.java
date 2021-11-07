package com.crud.playermanager.dao;

import com.crud.playermanager.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(PlayerRepository repository){

        return args -> {
            log.info("Preloading " + repository.save(new Player("Cristiano", "Ronaldo")));
            log.info("Preloading " + repository.save(new Player("Lionel", "Messi")));
            log.info("Preloading " + repository.save(new Player("Zlatan", "Ibrahimovic")));
            log.info("Preloading " + repository.save(new Player("Paulo", "Dybala")));
            log.info("Preloading " + repository.save(new Player("Kylian", "Mbappé")));
            log.info("Preloading " + repository.save(new Player("Robert", "Lewandowski")));
            log.info("Preloading " + repository.save(new Player("Neymar", "da Silva")));
            log.info("Preloading " + repository.save(new Player("Sergio", "Ramos")));
        };
    }
}
