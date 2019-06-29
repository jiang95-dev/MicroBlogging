package com.jjt.blogtopic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jjt.blogtopic.mapper.UserMapper;
import com.jjt.blogtopic.model.User;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.jjt.blogtopic"})
@Controller
@RequestMapping("/")
@MapperScan("com.jjt.blogtopic.mapper")
public class App 
{
	@Autowired
	private UserMapper userMapper;
	
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
    
    @RequestMapping("/")
    @ResponseBody
    public String home() {
    	return "home page";
    }
    
    @RequestMapping ("/index")
    public ModelAndView index(){
	    ModelAndView modelAndView = new ModelAndView("index");
	    User user = userMapper.selectByPrimaryKey(1);
	    modelAndView.addObject("content", user.getNickName());
	    return modelAndView;
    }
}


