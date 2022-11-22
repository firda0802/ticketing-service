/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.utility;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Riko
 */
public class HttpUtility {

    private HttpUtility() {}

    public static Map<String, List<String>> getHeader(HttpServletRequest httpServletRequest) {
        return Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(httpServletRequest.getHeaders(h))
                ));
    }

    public static String writeLogRequest(HttpServletRequest httpServletRequest, String request) {
        return "Incoming Request Header " + getHeader(httpServletRequest) + "\n"
                + "Method : " + httpServletRequest.getMethod() + "\n"
                + "Path   : " + httpServletRequest.getRequestURI() + "\n"
                + "Body   : " + request;
    }

    public static String writeLogResp(String request) {
        return "Response : "+request;
    }
}
