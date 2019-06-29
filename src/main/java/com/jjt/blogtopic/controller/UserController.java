package com.jjt.blogtopic.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jjt.blogtopic.model.User;
import com.jjt.blogtopic.service.ServiceResult;
import com.jjt.blogtopic.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	
	@Autowired
    private HttpServletRequest httpServletRequest;
	
	@RequestMapping(value="/get", method=RequestMethod.GET)
    @ResponseBody
	public User getUser(@RequestParam(name="id") Integer id) {
		return userService.getUser(id);
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map login(@RequestParam(name = "loginName")String loginName,
                     @RequestParam(name = "password")String password) throws UnsupportedEncodingException {
        ServiceResult result = userService.login(loginName, password);
        if(!result.isSuccess()){
            return getErrorResp((String) result.getData());
        }else{
            httpServletRequest.getSession().setAttribute("loginUser", result.getData());
            return getResp(result.getData());
        }
    }
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
    @ResponseBody
	public Map register(@RequestParam(name="loginName") String loginName, 
			@RequestParam(name="password") String password,
			@RequestParam(name="nickName") String nickName,
			@RequestParam(name="gender") Integer gender,
			@RequestParam(name="age") Integer age,
			@RequestParam(name="avatarUrl") String avatarUrl) throws UnsupportedEncodingException {
		
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickName) || StringUtils.isEmpty(avatarUrl) ){
            return getErrorResp("Invalid parameters");
        }
        if(gender.intValue() != 1 && gender.intValue() != 2){
            return getErrorResp("Invalid gender");
        }

        ServiceResult result = userService.register(loginName, password, nickName, gender, age, avatarUrl);
        
        if(result.isSuccess()){
            return getResp(null);
        }else{
            return getErrorResp((String) result.getData());
        }
	}
	
	@RequestMapping(value = "/login.html",method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView  = new ModelAndView("login");
        return modelAndView;
    }
	
	@RequestMapping (value = "/register.html",method = RequestMethod.GET)
    public ModelAndView index(){
	    ModelAndView modelAndView = new ModelAndView("register");
	    return modelAndView;
    }
}

