package com.endava.taskserver.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String email;

    private String password;

    @OneToMany
    @JoinColumn(name = "userId")
    private List<Authority> authorities;
}
