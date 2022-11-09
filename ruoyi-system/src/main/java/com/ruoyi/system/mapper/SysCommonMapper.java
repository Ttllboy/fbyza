package com.ruoyi.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface SysCommonMapper {
    public HashMap selectCheckUser(@Param("userName") String userName,@Param("userPassword") String userPassword);
}
