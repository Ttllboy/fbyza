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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/backApi")
public class ABackController {


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
            System.out.println("url:" + url);
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

    @PostMapping("/getRecordList")
    public List<HashMap> getRecordList(@RequestBody JSONObject questJson) throws Exception {//点击数字,查看月报表的记录list
        Date startDate = questJson.getDate("date");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = df.format(startDate);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(startDay.substring(0,4)), Integer.parseInt(startDay.substring(5,7)) - 1, 1);
        String firstDayOfMonth = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
//        System.out.println("第一天："+firstDayOfMonth);

        //这里先设置要获取月份的下月的第一天
        calendar.set(Integer.parseInt(startDay.substring(0,4)), Integer.parseInt(startDay.substring(5,7)), 1);
        //这里将日期值减去一天，从而获取到要求的月份最后一天
//        calendar.add(Calendar.DATE, -1);
        String lastDayOfMonth = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
//        System.out.println("最后一天："+lastDayOfMonth);

        startDate = df.parse(firstDayOfMonth);
        Date endDate = df.parse(lastDayOfMonth);
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setStartDate(startDate);
        checkRecord.setEndDate(endDate);
        return checkRecordMapper.selectMonthRecordList(checkRecord);
    }


}
