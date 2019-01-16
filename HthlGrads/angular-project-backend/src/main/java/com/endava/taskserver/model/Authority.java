package com.endava.taskserver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Authorities")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn()
    private int userId;

    private String role;

}
