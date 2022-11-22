package com.binar.tix.utility;

import javax.servlet.http.HttpServletResponse;

public class Constant {

    private Constant() {}

    public static final String SUKSES = "Sukses";
    public static final String GAGAL = "Proses Gagal";
    public static final Integer OK = HttpServletResponse.SC_OK;
    public static final Integer NO_CONTENT = HttpServletResponse.SC_NO_CONTENT;
    public static final Integer ALREADY_EXIST = HttpServletResponse.SC_CONFLICT;
    public static final Integer INTERNAL_ERROR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    public static final Integer BAD_REQUEST = HttpServletResponse.SC_BAD_REQUEST;
}
