package com.binar.tix.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "citizenship")
public class Citizenship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    public Citizenship(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
