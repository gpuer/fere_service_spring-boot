package com.fere.fere.mapper;

import com.fere.fere.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LoginMapper {
    @Select("select * from user where username=#{username}")
    public User login(User user);

    @Select("select count(*) from user where username=#{username}")
    public int userInspect(String username);

    @Insert("insert into user (username,password) values (#{username},#{password})")
    public void register(User user);

    @Insert("insert into information (uid,nickname) values(#{uid},#{username})")
    public void registerInfromation(User user);


}
