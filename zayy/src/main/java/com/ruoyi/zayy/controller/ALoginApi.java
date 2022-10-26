package com.ruoyi.zayy.controller;


import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.zayy.domain.CheckPlace;
import com.ruoyi.zayy.domain.CheckUser;
import com.ruoyi.zayy.mapper.CheckPlaceMapper;
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
    @Autowired
    CheckPlaceMapper checkPlaceMapper;

    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject jsonObject){
        JSONObject reJson = new JSONObject();
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String placeId = jsonObject.getString("placeId");
        CheckUser user = new CheckUser();
        user.setUserName(userName);
        List<CheckUser> list = checkUserMapper.loginSelect(user);
        if(list.size() > 0){
            String password2 = list.get(0).getUserPassword();
            if(password.equals(password2)){
                CheckPlace place = new CheckPlace();
                place.setPlaceId(placeId);
                List<CheckPlace> checkPlaces = checkPlaceMapper.selectCheckPlaceList(place);
                String placeName = null;
                if(checkPlaces.size() > 0){
                    placeName = checkPlaces.get(0).getPlaceName();
                }
                reJson.put("code",200);
                reJson.put("msg","登录成功");
                reJson.put("userName",list.get(0).getNickName());
                reJson.put("placeName",placeName);
                reJson.put("userId",list.get(0).getId());
                reJson.put("userRole",list.get(0).getUserRole());
                return reJson;
            }else{
                reJson.put("code",500);
                reJson.put("msg","用户名或密码输入错误");
                return reJson;
            }
        }else {
            reJson.put("code",500);
            reJson.put("msg","用户名或密码输入错误");
            return reJson;
        }
    }
}
