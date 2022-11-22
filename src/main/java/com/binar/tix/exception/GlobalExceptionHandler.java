package com.binar.tix.exception;

import com.binar.tix.payload.Messages;
import com.binar.tix.utility.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> globalExceptionHandling(Exception exception, HttpServletRequest request) {
        StringWriter errors = new StringWriter();
        exception.printStackTrace(new PrintWriter(errors));
        String allError = errors.toString();
        log.error(allError);
        return new ResponseEntity<>(new ExceptionMessage(exception.getMessage(), "Please contact the developer"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
