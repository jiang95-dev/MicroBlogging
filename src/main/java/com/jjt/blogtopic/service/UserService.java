package com.jjt.blogtopic.service;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jjt.blogtopic.mapper.UserMapper;
import com.jjt.blogtopic.model.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	public User getUser(Integer id) {
		if(id == null) throw new IllegalArgumentException("Id can't be null");
		return userMapper.selectByPrimaryKey(id);
	}
	
	public ServiceResult register(String loginName, String password, String nickName, Integer gender, Integer age, String avatarUrl) throws UnsupportedEncodingException {
        ServiceResult result = new ServiceResult();
		User user = new User();
		user.setLoginName(loginName);
		user.setPassword(encodeByMd5(password));
		user.setNickName(nickName);
		user.setGender(gender.byteValue());
		user.setAge(age);
		user.setAvatarUrl(avatarUrl);
		try {
			userMapper.insertSelective(user);
		} catch(DuplicateKeyException e) {
			result.setSuccess(false);
			result.setData("Login name duplicated");
		}
		return result;
	}
	
	public ServiceResult login(String loginName,String password) throws UnsupportedEncodingException {
        ServiceResult result = new ServiceResult();
        //根据登录名获取对应的一个用户信息
        User user = userMapper.selectByLoginName(loginName);
        if(user == null || !StringUtils.equals(encodeByMd5(password), user.getPassword())){
        	result.setSuccess(false);
        	result.setData("Login name or password is wrong");
            return result;
        }
        result.setSuccess(true);
        result.setData(user);
        return result;
    }
	
	private String encodeByMd5(String str) throws UnsupportedEncodingException {
        return DigestUtils.md5DigestAsHex(str.getBytes("utf-8"));
    }
}
