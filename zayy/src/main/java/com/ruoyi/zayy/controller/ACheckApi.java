package com.ruoyi.zayy.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.*;
import com.ruoyi.zayy.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checkApi")
public class ACheckApi {

    @Autowired
    CommonMapper commonMapper;
    @Autowired
    CheckUserMapper checkUserMapper;
    @Autowired
    CheckItemDeptMapper checkItemDeptMapper;
    @Autowired
    CheckItemMapper checkItemMapper;
    @Autowired
    CheckRecordMapper checkRecordMapper;

    //获取当前用户的巡检项
    @PostMapping("/getCheckItem")
    public List<HashMap> getCheckItem(@RequestBody JSONObject questJson){
        JSONArray reArray = new JSONArray();
        Long userId = questJson.getLong("userId");
        CheckUser user = checkUserMapper.selectCheckUserById(userId);
        int deptId = user.getUserDept();
        CheckItemDept checkItemDept = new CheckItemDept();
        checkItemDept.setDeptId((long) deptId);
        return checkItemDeptMapper.selectCheckItemDeptNameList((long) deptId);
    }

    //巡检初始换接口
    @PostMapping("/init")
    public JSONObject init(@RequestBody JSONObject questJson){
        CheckRecord record = new CheckRecord();
        record.setUserId(questJson.getLong("userId"));
        String recordId = String.valueOf(UUID.randomUUID());
        record.setRecordId(recordId);
        checkRecordMapper.insertCheckRecord(record);
        JSONObject reJson = new JSONObject();
        reJson.put("recordId",recordId);
        return reJson;
    }
    @PostMapping("/test")
    public void test(){
        RecordItem item = new RecordItem();
        item.setRecordId("asa");
        item.setItemId(1L);
        item.setItemIf(0);
        commonMapper.insertRecordItem(item);
    }

    //巡检提交接口
    @PostMapping("/submitData")
    public JSONObject submitData(@RequestBody JSONObject questJson){
        //判断是否扫码进入网页
        if(!questJson.containsKey("checkPlace")){
            JSONObject reJson = new JSONObject();
            reJson.put("code",200);
            reJson.put("msg","未检测到巡检地点");
            return reJson;
        }
        JSONArray itemArray = questJson.getJSONArray("itemArray");
        String recordId = questJson.getString("recordId");
        for (int i = 0; i < itemArray.size(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            RecordItem recordItem = new RecordItem();
            recordItem.setRecordId(recordId);
            recordItem.setItemId(item.getLong("itemId"));
            recordItem.setItemIf(item.getInteger("itemIf"));
            commonMapper.insertRecordItem(recordItem);
        }
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setRecordId(recordId);
        List<CheckRecord> list = checkRecordMapper.selectCheckRecordList(checkRecord);
        checkRecord = list.get(0);
        checkRecord.setCheckPlace(questJson.getString("checkPlace"));
        checkRecord.setRecordTime(questJson.getDate("recordTime"));
        checkRecord.setCheckContent(questJson.getString("checkContent"));
        insertImg(questJson.getJSONArray("imgArray"),recordId);
        Integer tip = checkRecordMapper.updateCheckRecord(checkRecord);
        if(tip == 1){
            JSONObject reJson = new JSONObject();
            reJson.put("code",200);
            reJson.put("msg","提交成功");
            return reJson;
        }else {
            JSONObject reJson = new JSONObject();
            reJson.put("code",500);
            reJson.put("msg","提交失败");
            return reJson;
        }
    }

    @Async("threadPoolTaskExecutor")
    public void insertImg(JSONArray imgArray,String recordId){
        CheckRecord record = new CheckRecord();
        record.setRecordId(recordId);
        List<CheckRecord> recordList = checkRecordMapper.selectCheckRecordList(record);
        Long cordId = recordList.get(0).getId();
        for (int i = 0; i < imgArray.size(); i++) {
            String itemImg = imgArray.getString(i);
            RecordImg recordImg = new RecordImg();
            recordImg.setRecordId(cordId);
            recordImg.setItemImg(itemImg);
            commonMapper.insertRecordImg(recordImg);
        }
    }




}
