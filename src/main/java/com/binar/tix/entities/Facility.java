package com.binar.tix.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "facility")
public class Facility implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facility")
    private Integer id;

    @Column(name = "class_id")
    private Integer classId;
    @Column(name = "facility_name")
    private String facilityName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "class_id", insertable = false, updatable = false)
    private ClassSeats classSeats;

    public Facility(String facilityName, Integer classId) {
        this.facilityName = facilityName;
        this.classId = classId;
    }
}
