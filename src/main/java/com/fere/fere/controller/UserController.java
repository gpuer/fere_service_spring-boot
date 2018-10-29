package com.fere.fere.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fere.fere.entity.Information;
import com.fere.fere.error.UserError;
import com.fere.fere.mapper.UserMapper;
import com.fere.fere.utils.AesEncode;
import com.fere.fere.utils.SensitiveWord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/link")
    public UserError link(String uid, HttpServletRequest request){
        String cookie1=request.getHeader("Cookie1");
        try{
            JSONObject cookie=JSON.parseObject(AesEncode.decrypt(cookie1));
            userMapper.link(uid,cookie.getString("uid"));
            return new UserError(11);
        }catch (Exception e){
            e.printStackTrace();
            return new UserError(12);
        }
    }
    @RequestMapping("/linkrequest")
    public UserError link(HttpServletRequest request){
        String cookie1=request.getHeader("Cookie1");
        try{
            JSONObject cookie=JSON.parseObject(AesEncode.decrypt(cookie1));
            Information information=userMapper.findLinkStatus(cookie.getInteger("uid"));
            if("".equals(information.getLink_uid()))
                return new UserError("无绑定申请");
            if(information.getLink_status()!=0)
                return new UserError();
            int link_id=Integer.valueOf(information.getLink_uid());
            information=userMapper.findLinkStatus(link_id);
            if(information.getLink_uid()==null)
                return new UserError(13);
            return new UserError(information.getNickname());
        }catch (Exception e){
            e.printStackTrace();
            return new UserError(999);
        }
    }
    @PostMapping("/confirm")
    public UserError confirm(int type, HttpServletRequest request){
        String cookie1=request.getHeader("Cookie1");
        try{
            JSONObject cookie=JSON.parseObject(AesEncode.decrypt(cookie1));
            int uid=cookie.getInteger("uid");
            if(type==1){
                userMapper.confirm(uid);
                return new UserError("绑定成功");
            }else{
                userMapper.unconfirm(uid);
                return new UserError("已取消绑定申请");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new UserError(999);
        }
    }
    @PostMapping("/updatenickname")
    public UserError updateNickname(String nickname,HttpServletRequest request){
        SensitiveWord sensitiveWord=new SensitiveWord();
        String cookie1=request.getHeader("Cookie1");
        try{
            JSONObject cookie=JSON.parseObject(AesEncode.decrypt(cookie1));
            int uid=cookie.getInteger("uid");
            if(sensitiveWord.filterInfo(nickname))//敏感词检查
                return new UserError(15);
            userMapper.updateNickname(uid,nickname);
            return new UserError(14);
        }catch (Exception e){
            e.printStackTrace();
            return new UserError(999);
        }
    }
    @PostMapping("/addphone")
    public UserError addPhone(String phone,HttpServletRequest request){
        SensitiveWord sensitiveWord=new SensitiveWord();
        String cookie1=request.getHeader("Cookie1");
        try{
            JSONObject cookie=JSON.parseObject(AesEncode.decrypt(cookie1));
            int uid=cookie.getInteger("uid");
            userMapper.addEmergencyContact(uid,phone);
            return new UserError("绑定成功");
        }catch (Exception e){
            e.printStackTrace();
            return new UserError(16);
        }
    }
}
