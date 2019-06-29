package com.jjt.blogtopic.controller;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {
	protected Map getResp(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", data);
        return result;
    }

    protected Map getErrorResp(String errMsg){
        Map<String,Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", errMsg);
        return result;
    }
}
