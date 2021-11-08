package com.crud.playermanager.resources;

import com.crud.playermanager.dao.PlayerRepository;
import com.crud.playermanager.model.Player;
import com.crud.playermanager.util.CustomErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class PlayerResources {

    @Autowired
    private PlayerRepository playerRepository ;

    // ------------------------Retrieve All Players------------------------
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

    // ---------------------Retrieve All Players names---------------------
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

    // ------------------------Retrieve Single Player------------------------
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

    // -------------------Retrieve one or more Player by name-------------------
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

    // ---------------------------Create new Players-----------------------------
    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player,
                                               UriComponentsBuilder ucBuilder){
        log.info("Creating player : {}", player);
        if(playerRepository.existsByFirstNameAndLastName(
                player.getFirstName(), player.getLastName())){
            log.error("Unable to create. A Player with name {} {} already exist",
                    player.getFirstName(), player.getLastName());
            return new ResponseEntity(
                    new CustomErrorType("Unable to create. A User with name " +
                    player.getFirstName() + " "
                    + player.getLastName() + " already exist."),HttpStatus.CONFLICT);
        }
        Player createdPlayer = playerRepository.save(player) ;
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/players/{id}")
                .buildAndExpand(player.getId()).toUri());
        return new ResponseEntity<Player>(createdPlayer, headers, HttpStatus.CREATED);
    }

    // -----------------------------Update a Player-------------------------------
    @PutMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id,
                                               @RequestBody Player player){

        log.info("Updating Player with id: {} ", id);
        Optional<Player> currentPlayer = playerRepository.findById(id);

        if (!currentPlayer.isPresent()){
            log.error("Unable to update. Player with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Player with id "
                    + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentPlayer.get().setFirstName(player.getFirstName());
        currentPlayer.get().setLastName(player.getLastName());

        playerRepository.save(currentPlayer.get()) ;

        return new ResponseEntity<Player>(currentPlayer.get(), HttpStatus.OK) ;
    }

    // -----------------------------Delete a Player-------------------------------
    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Player with id {}", id);

        Optional<Player> player = playerRepository.findById(id);
        if (!player.isPresent()) {
            log.error("Unable to delete. Player with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Player with id "
                    + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        playerRepository.deleteById(id);
        return new ResponseEntity<Player>(HttpStatus.NO_CONTENT);
    }

    // -----------------------------Delete All Players-------------------------------
    @DeleteMapping("/players")
    public ResponseEntity<?> deleteAllPlayer() {
        log.info("Fetching & Deleting All Players");

        playerRepository.deleteAll();
        return new ResponseEntity<Player>(HttpStatus.NO_CONTENT);
    }
}
