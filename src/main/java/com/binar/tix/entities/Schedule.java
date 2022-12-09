package com.binar.tix.entities;

import com.binar.tix.payload.ScheduleAirplane;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "schedule")
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_seq")
    @SequenceGenerator(name = "schedule_seq", sequenceName = "schedule_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "schedule_id", nullable = false)
    private Integer scheduleId;

    @Column(name = "destination_id")
    private Integer destinationId;

    @ManyToOne
    @JoinColumn(name = "destination_id", insertable = false, updatable = false)
    private Destination destination;

    @Column(name = "class_id")
    private Integer classId;

    @ManyToOne
    @JoinColumn(name = "class_id", insertable = false, updatable = false)
    private ClassSeats classSeats;

    @Column(name = "price_id")
    private Integer priceId;

    @Column(name = "airplanes_id")
    private Integer airplanesId;

    @Column(name = "price")
    private Integer price;

    @Column(name = "flight")
    private String flight;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "airplanes_id", insertable = false, updatable = false)
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name = "price_id", insertable = false, updatable = false)
    private Pricing pricing;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+7")
    @Column(name = "start_time")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+7")
    @Column(name = "end_time")
    private LocalTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    @Column(name = "flight_date")
    private LocalDate flightDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:Ss", timezone = "GMT+7")
    @CreationTimestamp
    @Column(name = "cdate")
    private LocalDateTime cdate;


    public Schedule(Integer destinationId, Integer classId, Integer priceId, LocalTime startTime, LocalTime endTime, LocalDate flightDate, ScheduleAirplane scheduleAirplane) {
        this.destinationId = destinationId;
        this.classId = classId;
        this.priceId = priceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.flightDate = flightDate;
        this.airplanesId = scheduleAirplane.getAirplanesId();
        this.price = scheduleAirplane.getPrice();
        this.flight = scheduleAirplane.getFlight();
    }
}


