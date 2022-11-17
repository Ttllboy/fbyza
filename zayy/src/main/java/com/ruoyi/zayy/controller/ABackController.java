package com.ruoyi.zayy.controller;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.*;
import com.ruoyi.zayy.mapper.*;
import com.ruoyi.zayy.util.QRCodeUtil;
import netscape.javascript.JSObject;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/backApi")
public class ABackController {

    @Autowired
    private RecordAjMapper recordAjMapper;

    @PostMapping("/ajInsert")
    public Integer ajInsert(@RequestBody JSONObject questJson) {
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
    public String addCheckUser(@RequestBody JSONArray questArray) {
        for (int i = 0; i < questArray.size(); i++) {
            JSONObject jsonObject = questArray.getJSONObject(i);
            CheckUser user = new CheckUser(jsonObject.getString("userName"), jsonObject.getString("userPassword"), jsonObject.getInteger("userRole"), jsonObject.getString("nickName"), jsonObject.getString("userDept"));
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
            String url = RuoYiConfig.getWebUrl() + "/" + "?placeId=" + placeId + "&corpId=ding9c8ccab5f3f96c1435c2f4657eb6378f";
            String logoPath = RuoYiConfig.getLogoUrl();
            String destPath = RuoYiConfig.getCheckImg();
            String fileName = QRCodeUtil.encode(url, logoPath, destPath, true);
            checkPlace.setPlaceId(placeId);
            checkPlace.setPlaceImg(fileName);
            checkPlace.setPlaceName(jsonObject.getString("placeName"));
            checkPlace.setSpecialOffice(jsonObject.getInteger("specialOffice"));
            checkPlaceMapper.insertCheckPlace(checkPlace);
        }
        return "新增二维码成功";
    }

    @Autowired
    CheckItemMapper checkItemMapper;

    //批量新增巡检项接口
    @PostMapping("/addItems")
    public String addItems(@RequestBody JSONArray questArray) throws Exception {
        for (int i = 0; i < questArray.size(); i++) {
            JSONObject jsonObject = questArray.getJSONObject(i);
            CheckItem item = new CheckItem();
            item.setItemName(jsonObject.getString("itemName"));
            item.setItemAbnormal(jsonObject.getInteger("itemAbnormal"));
            item.setAbnormalLev(jsonObject.getInteger("abnormalLev"));
            item.setSpecialOffice(jsonObject.getInteger("specialOffice"));
            checkItemMapper.insertCheckItem(item);
        }
        return "新增巡检项成功";
    }
    @Autowired
    CheckRecordMapper checkRecordMapper;
    //获取日 & 月报表
    @PostMapping("/getForm")
    public JSONArray getForm(@RequestBody JSONObject questJson) {
        Integer roleId = questJson.getInteger("roleId");
        Long userId = questJson.getLong("userId");
        String userDept = questJson.getString("userDept");
        Long officeId = questJson.getLong("officeId");
        Integer formType = questJson.getInteger("formType");
        Date startDate = questJson.getDate("date");
        Date endDate = new Date(startDate.getTime() + (24*60*60*1000));
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setStartDate(startDate);
        checkRecord.setEndDate(endDate);
        JSONArray reArray = new JSONArray();
        if (formType == 0) {  //日报表
            switch (roleId) {
                case 1:  //超级管理员可以看到所有的巡检报表
                case 6:  //院领导可以看到所有巡检报表
                case 2: {  //管理员可以看到所有的巡检报表
                    List<CheckRecord> list = checkRecordMapper.selectCheckRecordDayAll(checkRecord);
                    for (int i = 0; i < list.size(); i++) {
                        CheckRecord recordItem = list.get(i);
                        JSONObject item = new JSONObject();
                        item.put("checkPlace",recordItem.getCheckPlace());
                        item.put("placeName",recordItem.getPlaceName());
                        item.put("isCheck",1);
                        item.put("notCheck",0);
                        reArray.add(item);
                    }
                    return reArray;
                }
                case 7:{  //巡检员可以看到自己的巡检记录
                    checkRecord.setUserId(userId);
                    List<CheckRecord> list = checkRecordMapper.selectCheckRecordDayXjy(checkRecord);
                    for (int i = 0; i < list.size(); i++) {
                        CheckRecord recordItem = list.get(i);
                        JSONObject item = new JSONObject();
                        item.put("checkPlace",recordItem.getCheckPlace());
                        item.put("placeName",recordItem.getPlaceName());
                        item.put("isCheck",1);
                        item.put("notCheck",0);
                        reArray.add(item);
                    }
                    return reArray;
                }
            }
        }
        return null;
    }
}
