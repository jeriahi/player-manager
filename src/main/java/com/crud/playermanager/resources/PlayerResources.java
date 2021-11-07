package com.crud.playermanager.resources;

import com.crud.playermanager.dao.PlayerRepository;
import com.crud.playermanager.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class PlayerResources {

    @Autowired
    private PlayerRepository playerRepository ;

    @GetMapping("/players")
    public ResponseEntity<List<Player>> all(){
        List<Player> players = playerRepository.findAll() ;
        if (players.isEmpty()){
            log.info("There is no Player to display");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        log.info("Loading players ...");
        return new ResponseEntity(players , HttpStatus.OK) ;
    }
}
