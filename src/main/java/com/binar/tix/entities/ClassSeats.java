package com.binar.tix.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "class")
public class ClassSeats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    public ClassSeats() {
    }

    public ClassSeats(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
