package com.jjt.blogtopic.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public Map doErrorMsg(HttpServletRequest request, HttpServletResponse response, Exception e){
        e.printStackTrace();
        Map<String,Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", "Exception");
        return result;
    }
}
