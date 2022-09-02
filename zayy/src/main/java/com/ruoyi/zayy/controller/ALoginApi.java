package com.ruoyi.zayy.controller;


import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.zayy.domain.CheckUser;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loginApi")
public class ALoginApi {

    @Autowired
    private CheckUserMapper checkUserMapper;

    @PostMapping("/login")
    public void login(@RequestBody JSONObject jsonObject){
        JSONObject reJson = new JSONObject();
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        CheckUser user = new CheckUser();
        user.setUserName(userName);
        List<CheckUser> list = checkUserMapper.loginSelect(user);
        if(list.size() > 0){
            System.out.println("用户名存在，开始判断密码");
            String password2 = list.get(0).getUserPassword();
            if(password.equals(password2)){


            }
        }


    }
}
