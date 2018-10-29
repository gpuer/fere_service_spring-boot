package com.fere.fere.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fere.fere.error.LoginError;
import com.fere.fere.utils.AesEncode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class UserConfig implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String Cookie=request.getHeader("Cookie1");
        response.setHeader("Content-Type","text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        JSONObject j= JSON.parseObject(AesEncode.decrypt(Cookie));
        if(j==null){
            String json;
            try {
                json=JSON.toJSONString(new LoginError(5));
                PrintWriter out=response.getWriter();
                out.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
