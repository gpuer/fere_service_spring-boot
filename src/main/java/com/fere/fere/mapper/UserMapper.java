package com.fere.fere.mapper;

import com.fere.fere.entity.Information;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Update("update information set link_uid=#{uid},link_status=0 where uid=#{link_uid}")
    public void link(@Param("link_uid") String link_uid, @Param("uid") String uid);
    @Select("select * from information where uid=#{uid}")
    public Information findLinkStatus(int uid);
    @Update("update information set link_status=1 where uid=#{uid}")
    public void confirm(int uid);
    @Update("update information set link_uid='' where uid=#{uid}")
    public void unconfirm(int uid);
    @Update("update information set nickname=#{nickname} where uid=#{uid}")
    public void updateNickname(@Param("uid") int uid,@Param("nickname") String nickname);
    @Update("update information set EmergencyContact=#{phone} where uid=#{uid}")
    public void addEmergencyContact(@Param("uid") int uid,@Param("phone") String phone);

}
