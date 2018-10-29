package com.fere.fere.controller;

import com.alibaba.fastjson.JSON;
import com.fere.fere.entity.User;
import com.fere.fere.error.LoginError;
import com.fere.fere.mapper.LoginMapper;
import com.fere.fere.utils.AesEncode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginController {
    @Autowired
    private LoginMapper loginMapper;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @PostMapping("/login")
    public LoginError login(User user, HttpServletRequest request, HttpServletResponse response){
        User tempUser= loginMapper.login(user);
        if(tempUser==null)
            return new LoginError(3);
        System.out.println(tempUser.toString());
        if(!tempUser.getPassword().equals(user.getPassword()))
            return new LoginError(1);
        Map<String,String> cookie_map=new HashMap<>();
        cookie_map.put("uid",String.valueOf(tempUser.getUid()));
        cookie_map.put("username",tempUser.getUsername());
        cookie_map.put("login","true");
        String encodeCookie=AesEncode.encrypt(JSON.toJSONString(cookie_map));
        response.addHeader("Cookie1",encodeCookie);
        return new LoginError(String.valueOf(tempUser.getUid()));
    }
    @PostMapping("/register")
    public LoginError register(User user){
        String username=user.getUsername();
        String pw=user.getPassword();
        if(username==null || pw==null)
            return new LoginError(3);
        int type= loginMapper.userInspect(username);
        if(type>0)
            return new LoginError(2);
        try{
            loginMapper.register(user);
            user= loginMapper.login(user);
            loginMapper.registerInfromation(user);
        }catch (Exception e){
            return new LoginError(999);
        }


        return new LoginError();
    }
    @PostMapping("/userinspect")
    public LoginError userInspect(String username){
        int type= loginMapper.userInspect(username);
        if(type>0)
            return new LoginError(2);
        return new LoginError();
    }
}
