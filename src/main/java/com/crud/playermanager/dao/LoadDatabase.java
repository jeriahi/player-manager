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
            log.info("Preloading " + repository.save(new Player("Ronaldo", "Cristiano")));
            log.info("Preloading " + repository.save(new Player("Messi", "Lionel")));
            log.info("Preloading " + repository.save(new Player("Ibrahimovic", "Zlatan")));
            log.info("Preloading " + repository.save(new Player("Dybala", "Paulo")));
            log.info("Preloading " + repository.save(new Player("Mbappé", "Kylian")));
            log.info("Preloading " + repository.save(new Player("Lewandowski", "Robert")));
            log.info("Preloading " + repository.save(new Player("da Silva", "Neymar")));
            log.info("Preloading " + repository.save(new Player("Ramos", "Sergio")));
            log.info("Preloading " + repository.save(new Player("Ronaldo", "de Lima")));
        };
    }
}
