package com.esprit.spring.ftthback.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_Id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column
    private  String numtel;
    @Column
    private String code;
    @Column
    private String refnv;
    //admin
    @Column
    private String grade;
    @Column
    private String status;
    private String resetPasswordToken;
    private String registerToken;
    @Column(name = "verification")
    private Long verification;
    //@Column(name="verify")
    //private boolean verify;
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_Id") },
            inverseJoinColumns = { @JoinColumn(name = "role_Id") })
    private List<Role> roles;

    public User(String username, String password, String code, String numtel, String refnv, String grade, String status, String email) {  this.username = username;
        this.password = password;
        this.email = email;
        this.numtel = numtel;
        this.refnv = refnv;
        this.code=code;
        this.grade = grade;
        this.status = status;
    }

    @PrePersist
    public void generateCode() {
        this.verification = (long) (Math.random() * 1000000);
    }
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Demande> demandes;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reclamation> reclamations;







}


