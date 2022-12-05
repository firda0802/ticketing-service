package com.binar.tix.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "admin")

public class Admin implements Serializable{
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_seq")
   @SequenceGenerator(name = "admin_seq", sequenceName = "id_admin_seq", initialValue = 1, allocationSize = 1)
   @Column(name = "id_admin", nullable = false)
   private Integer id_admin;

   @Column(name = "user_name")
   private String username;

   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   @Column(name = "password")
   private String password;

   @Column(name = "name")
   private String name;

   @Column(name = "status")
   private Boolean status;

}
