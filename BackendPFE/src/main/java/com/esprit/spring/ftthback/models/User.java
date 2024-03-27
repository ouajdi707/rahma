package com.esprit.spring.ftthback.models;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

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

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_Id") },
            inverseJoinColumns = { @JoinColumn(name = "role_Id") })
    private List<Role> roles;

    public User(Long id, String username, String password, String email, String numtel, String refnv,  String grade, String status, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.numtel = numtel;
        this.refnv = refnv;

        this.grade = grade;
        this.status = status;
        this.roles = roles;
    }
    public User(String username, String password, String email, String numtel, String refnv,  String grade, String status, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.numtel = numtel;
        this.refnv = refnv;

        this.grade = grade;
        this.status = status;
        this.roles = roles;
    }


    public User() {

    }

    public User(String username, String password, String numtel, String refnv, String grade, String status, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.numtel = numtel;
        this.refnv = refnv;
this.code=code;
        this.grade = grade;
        this.status = status;

    }

    public User(String username, String password, String code, String numtel, String refnv, String grade, String status, String email) {this.username = username;
        this.password = password;
        this.email = email;
        this.numtel = numtel;
        this.refnv = refnv;
        this.code=code;
        this.grade = grade;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumtel() {
        return numtel;
    }

    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }

    public String getRefnv() {
        return refnv;
    }

    public void setRefnv(String refnv) {
        this.refnv = refnv;
    }



    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


