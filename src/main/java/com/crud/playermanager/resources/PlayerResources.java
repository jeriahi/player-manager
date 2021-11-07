package com.crud.playermanager.resources;

import com.crud.playermanager.dao.PlayerRepository;
import com.crud.playermanager.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class PlayerResources {

    @Autowired
    private PlayerRepository playerRepository ;

    @GetMapping("/players")
    public ResponseEntity<List<Player>> allPlayers(){

        List<Player> players = playerRepository.findAll() ;
        if (players.isEmpty()){
            log.info("There is no Player to display");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        log.info("Loading players ...");
        return new ResponseEntity(players , HttpStatus.OK) ;
    }

    @GetMapping("/players/names")
    public ResponseEntity<Player> allPlayersNames(){

        List<Player> players = playerRepository.findAll() ;
        if (players.isEmpty()){
            log.info("There is no Player have this name to display");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        log.info("Loading players names ... ");
        return new ResponseEntity(players
                .stream()
                .map(player->player.getFirstName()).collect(Collectors.toList()),
                HttpStatus.OK) ;
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> onePlayer(@PathVariable Long id){

        Optional<Player> player = playerRepository.findById(id) ;
        if (!player.isPresent()){
            log.info("There is no Player with id " + id + " to display");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        log.info("Loading player with id " + id);
        return new ResponseEntity(player , HttpStatus.OK) ;
    }

    @GetMapping("/players/names/{name}")
    public ResponseEntity<Player> allPlayersByName(@PathVariable String name){

        List<Player> players = playerRepository.findAllByName(name) ;
        if (players.isEmpty()){
            log.info("There is no Player to display");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        log.info("Loading players with name contain: " + name + " ...");
        return new ResponseEntity(players, HttpStatus.OK) ;
    }
}
