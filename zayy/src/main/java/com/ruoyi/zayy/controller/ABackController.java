package com.ruoyi.zayy.controller;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.CheckPlace;
import com.ruoyi.zayy.domain.CheckUser;
import com.ruoyi.zayy.domain.RecordAj;
import com.ruoyi.zayy.mapper.CheckPlaceMapper;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import com.ruoyi.zayy.mapper.RecordAjMapper;
import com.ruoyi.zayy.util.QRCodeUtil;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/backApi")
public class ABackController {

    @Autowired
    private RecordAjMapper recordAjMapper;

    @PostMapping("/ajInsert")
    public Integer ajInsert(@RequestBody JSONObject questJson){
//        String securityIp = questJson.getString("securityIp");
//        String securityNum = questJson.getString("securityNum");
//        String securityImg = questJson.getString("securityImg");
//        Date securityTime = questJson.getDate("securityTime");
//        RecordAj recordAj = new RecordAj();
//        recordAj.setSecurityIp(securityIp);
//        recordAj.setSecurityNum(securityNum);
//        recordAj.setSecurityImg(securityImg);
//        recordAj.setSecurityTime(securityTime);
//        recordAjMapper.insertRecordAj(recordAj);
//        return 1;
        return null;
    }
    @Autowired
    CheckUserMapper checkUserMapper;

    //批量新增人员管理的接口
    @PostMapping("/addCheckUser")
    public String addCheckUser(@RequestBody JSONArray questArray){
        for (int i = 0; i < questArray.size(); i++) {
            JSONObject jsonObject = questArray.getJSONObject(i);
            CheckUser user = new CheckUser(jsonObject.getString("userName"),jsonObject.getString("userPassword"),jsonObject.getInteger("userRole"),jsonObject.getString("nickName"),jsonObject.getString("userDept"));
            checkUserMapper.insertCheckUser(user);
        }
        return "新增人员成功";
    }
    @Autowired
    CheckPlaceMapper checkPlaceMapper;
    //批量新增二维码的接口
    @PostMapping("/addQrCode")
    public String addQrCode(@RequestBody JSONArray questArray) throws Exception {
        for (int i = 0; i < questArray.size(); i++) {
            JSONObject jsonObject = questArray.getJSONObject(i);
            CheckPlace checkPlace = new CheckPlace();
            String placeId = String.valueOf(UUID.randomUUID());
            String url = RuoYiConfig.getWebUrl()+"/"+"?placeId="+placeId+"&corpId=ding1d34adf2092c528cee0f45d8e4f7c288";
            String logoPath = RuoYiConfig.getLogoUrl();
            String destPath = RuoYiConfig.getCheckImg();
            String fileName = QRCodeUtil.encode(url,logoPath,destPath,true);
            checkPlace.setPlaceId(placeId);
            checkPlace.setPlaceImg(fileName);
            checkPlace.setPlaceName(jsonObject.getString("placeName"));
            checkPlaceMapper.insertCheckPlace(checkPlace);
        }
        return "新增二维码成功";
    }

}
