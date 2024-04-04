package com.esprit.spring.ftthback.models;

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
@Table(name="reclamation")
public class Reclamation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description ;
    @Enumerated(EnumType.STRING)
    @Column(name = "Etat")
    private Etat etat;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
