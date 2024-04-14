package com.esprit.spring.ftthback.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Demande")
public class Demande {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="demande_Id")
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "acces")
    private String acces ;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type ;

    @Enumerated(EnumType.STRING)
    @Column(name = "Etat")
    private Etat etat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
