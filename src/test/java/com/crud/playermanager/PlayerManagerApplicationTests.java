package com.crud.playermanager;

import com.crud.playermanager.dao.PlayerRepository;
import com.crud.playermanager.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayerManagerApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    PlayerRepository playerRepository ;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllPlayerShouldReturnTheRightSize() throws Exception {

        List<Player> players = this.restTemplate.getForObject("http://localhost:" + port + "/api/players",
                List.class);
        assertThat(players.size()).isEqualTo(playerRepository.findAll().size());
    }

    @Test
    public void getOnePlayerShouldReturnTheRightOne() throws Exception {

        Player player = this.restTemplate.getForObject("http://localhost:" + port + "/api/players/1",
                Player.class);
        assertThat(player).isEqualTo(playerRepository.findById(1L).get());
    }

    @Test
    public void getOnePlayerNamesShouldReturnTheRightSize() throws Exception {

        List<String> playersName = this.restTemplate.getForObject("http://localhost:" + port + "/api/players/names",
                List.class);
        assertThat(playersName.size()).isEqualTo(playerRepository.findAll().size());
        assertThat(playersName).contains("Ronaldo");
    }

}