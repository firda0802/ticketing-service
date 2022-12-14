package com.binar.tix.utility;

import javax.servlet.http.HttpServletResponse;

public class Constant {

    private Constant() {}

    public static final String KEY1 = "QiFuYXIgUzNydmljZSAkJC";
    public static final String KEY2 = "MgRmluYWwgUHJvajNjdEAqKg";
    public static final String STAGING_QR = "https://ticket-app-5-staging.vercel.app/qr-validation?token=";
    public static final String PROD_QR = "https://ticket-app-5.vercel.app/qr-validation?token=";
    public static final String AUTH = "Authorization";
    public  static final String ENCRYPT_KEY = "ThisIsASecretKey";
    public  static final String DOMESTIK = "domestik";
    public  static final String INTER = "international";
    public static final String EMAIL = "email";
    public static final String USER_ID = "userId";
    public static final String SUKSES = "Sukses";
    public static final String GAGAL = "Proses Gagal";
    public static final Integer OK = HttpServletResponse.SC_OK;
    public static final Integer NO_CONTENT = HttpServletResponse.SC_NO_CONTENT;
    public static final Integer ALREADY_EXIST = HttpServletResponse.SC_CONFLICT;
    public static final Integer INTERNAL_ERROR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    public static final Integer BAD_REQUEST = HttpServletResponse.SC_BAD_REQUEST;
}
