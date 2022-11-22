/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Riko
 */
@Entity
@Getter
@Setter
@Table(name = "role_user")
public class RoleUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_user_seq")
    @SequenceGenerator(name = "role_user_seq", sequenceName = "role_user_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "role_id")
    private Integer roleId;
    
    @Column(name = "role_user")
    private String roleName;

    public RoleUser() {
    }

    public RoleUser(String roleUser) {
        this.roleName = roleUser;
    }
}
