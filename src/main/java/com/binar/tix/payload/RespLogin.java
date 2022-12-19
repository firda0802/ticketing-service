/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Riko
 */
@Getter @Setter
public class RespLogin {
    private String token;
    private String role;
}
