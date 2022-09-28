package com.ruoyi.zayy.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.*;
import com.ruoyi.zayy.mapper.*;
import com.ruoyi.zayy.util.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    @Autowired
    CheckPlaceMapper checkPlaceMapper;

    //获取当前用户的巡检项
//    @PostMapping("/getCheckItem")
//    public List<HashMap> getCheckItem(@RequestBody JSONObject questJson){
//        JSONArray reArray = new JSONArray();
//        Long userId = questJson.getLong("userId");
//        CheckUser user = checkUserMapper.selectCheckUserById(userId);
//        String deptId = user.getUserDept();
//        CheckItemDept checkItemDept = new CheckItemDept();
//        checkItemDept.setDeptId((long) deptId);
//        return checkItemDeptMapper.selectCheckItemDeptNameList((long) deptId);
//        return null;
//    }

    ////获取当前巡检地点的巡检项2 9.20
    @PostMapping("/getCheckItem")
    public List<HashMap> getCheckItem(@RequestBody JSONObject questJson){
        JSONArray reArray = new JSONArray();
        String placeId = questJson.getString("placeId");
        CheckPlace place = new CheckPlace();
        place.setPlaceId(placeId);
        List<CheckPlace> list = checkPlaceMapper.selectCheckPlaceList(place);
        if(list.size() > 0){
            Long deptId = list.get(0).getId();
            return checkItemDeptMapper.selectCheckItemDeptNameList(deptId);
        }
        return null;
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
            reJson.put("code",500);
            reJson.put("msg","未检测到巡检地点");
            return reJson;
        }
        if(questJson.getString("checkPlace") == ""){
            JSONObject reJson = new JSONObject();
            reJson.put("code",500);
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

    //根据用户ID查询巡检记录
    @PostMapping("/getCheckRecord")
    public List<?> getCheckRecord(@RequestBody JSONObject questJson){
        Long userId = questJson.getLong("userId");
        CheckRecord record = new CheckRecord();
        record.setUserId(userId);
        List<HashMap> list = checkRecordMapper.selectCheckRecordNameList(record);
        for (int i = 0; i < list.size(); i++) {
            HashMap map = list.get(i);
            if(!map.containsKey("place_name")){
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    //根据巡检记录ID查询巡检记录详情
    @PostMapping("/getRecordDetail")
    public JSONObject getRecordDetail(@RequestBody JSONObject questJson){
        String recordId = questJson.getString("recordId");
        CheckRecord record = new CheckRecord();
        record.setRecordId(recordId);
        List<HashMap> recordList = checkRecordMapper.selectCheckRecordByRecordId(record);
        HashMap map = recordList.get(0);
        List<HashMap> recordItems = commonMapper.selectRecordItems(recordId);
        List<HashMap> recordImgs = commonMapper.selectRecordImgs((Long) map.get("id"));
        for (int i = 0; i < recordImgs.size(); i++) {
            HashMap map2 = recordImgs.get(i);
            String placeImg = (String) recordImgs.get(i).get("item_img");
            map2.put("item_img",RuoYiConfig.getApiImgUrl()+placeImg);
        }
        JSONObject reJson = new JSONObject();
        reJson.put("record",recordList.get(0));
        reJson.put("items",recordItems);
        reJson.put("imgs",recordImgs);

        return reJson;
    }

    //测试插入巡检图片
    @PostMapping("/testInsert")
    public void testInsert()throws Exception{
        CheckPlace checkPlace = new CheckPlace();
        checkPlace.setPlaceName("五病区");
        String placeId = String.valueOf(UUID.randomUUID());
        String url = RuoYiConfig.getWebUrl()+"?placeId="+placeId;
        String logoPath = RuoYiConfig.getLogoUrl();
        String destPath = RuoYiConfig.getCheckImg();
        String fileName = QRCodeUtil.encode(url,logoPath,destPath,true);
        checkPlace.setPlaceId(placeId);
        checkPlace.setPlaceImg(fileName);
        checkPlaceMapper.insertCheckPlace(checkPlace);
    }

    //测试分割数组
    @PostMapping("/ttest")
    public int getIntArray(@RequestBody JSONObject jsonObject){

        Date date1 = new Date();
        Date date2 = jsonObject.getDate("date2");
        Long s = date2.getTime();
        Long ss = date1.getTime();
        if (s > ss){
            System.out.println(1);
        }
        return 1;
    }
    @Autowired
    private CheckTaskMapper checkTaskMapper;

    //查看用户巡检任务
    @PostMapping("/userTask")
    public JSONObject userTask(@RequestBody JSONObject questJson){
        JSONObject reJson = new JSONObject();

        Long userId = questJson.getLong("userId");
        Integer page = questJson.getInteger("page");
        Integer pageSize = questJson.getInteger("pageSize");
        Date startDate = questJson.getDate("startDate");
        Date endDate = questJson.getDate("endDate");
        //0未完成1已完成
        Integer isNot = questJson.getInteger("isNot");

        CheckTask task = new CheckTask();
        task.setUserId(userId);
        task.setTaskType(1);
        if(startDate != null){
            task.setStartDate(startDate);
        }
        if(endDate != null){
            task.setEndDate(endDate);
        }
        task.setIsNot(isNot);

        PageHelper.startPage(page,pageSize);
        List<HashMap> list = checkTaskMapper.selectTaskListPage(task);
        PageInfo<HashMap> pageInfo = new PageInfo<>(list);
        reJson.put("data",pageInfo.getList());
        reJson.put("total",pageInfo.getTotal());
        return reJson;
    }

    @PostMapping("/testaa")
    public void testaa(){
        Integer tip = 20;
        for (Integer i = 0; i < tip; i++) {
            CheckTask task = new CheckTask();
            task.setTaskId(String.valueOf(UUID.randomUUID()));
            task.setUserId(1L);
            task.setReleaseTime(new Date());
            Long dateTime = new Date().getTime();
            dateTime = dateTime + 1000000;
            task.setDeadline(new Date(dateTime));
            task.setDeptId("6");
            task.setIsNot(0);
            task.setTaskType(1);
            checkTaskMapper.insertCheckTask(task);
        }

    }
}
