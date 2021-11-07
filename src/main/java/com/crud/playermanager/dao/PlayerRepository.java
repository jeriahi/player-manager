package com.crud.playermanager.dao;

import com.crud.playermanager.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player , Long> {

    @Query("select p from Player p " +
            "where p.firstName like %?1%" +
            "order by p.firstName asc ")
    public List<Player> findAllByName(String name) ;

    public boolean existsByFirstNameAndLastName(String firstName, String lastName) ;
}
