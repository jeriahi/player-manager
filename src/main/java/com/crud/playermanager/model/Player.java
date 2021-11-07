package com.crud.playermanager.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Player {

    @Id
    @GeneratedValue
    private Long Id ;
    @NonNull
    private String lastname ;
    @NonNull
    private String firstname ;


}
