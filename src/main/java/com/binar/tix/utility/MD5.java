/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Riko
 */
public class MD5 {

    private MD5() {}

    private static final Logger LOG = LoggerFactory.getLogger(MD5.class);

    public static String encrypt(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }
}
