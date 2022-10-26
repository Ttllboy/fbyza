package com.ruoyi.zayy.controller;

import ch.qos.logback.core.pattern.ConverterUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.*;
import com.ruoyi.zayy.mapper.*;
import com.ruoyi.zayy.util.ImgToBase64;
import com.ruoyi.zayy.util.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
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
//        CheckRecord record = new CheckRecord();
//        record.setUserId(questJson.getLong("userId"));
//        String recordId = String.valueOf(UUID.randomUUID());
//        record.setRecordId(recordId);
//        checkRecordMapper.insertCheckRecord(record);
//        JSONObject reJson = new JSONObject();
//        reJson.put("recordId",recordId);
//        return reJson;
        return null;
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

        //判断是否有巡检异常
        List<CheckItem> checkItemList = new ArrayList<>();
        JSONObject abnormalJson = new JSONObject();
        for (int i = 0; i < itemArray.size(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            Long itemId = item.getLong("itemId");
            Integer itemIf = item.getInteger("itemIf");
            CheckItem checkItem = checkItemMapper.selectCheckItemById(itemId);
            Integer itemAbnormal = checkItem.getItemAbnormal();
            if(itemIf == itemAbnormal){
                checkItemList.add(checkItem);
            }
        }
        if(checkItemList.size() > 0){
            abnormalJson.put("code",300);
            abnormalJson.put("msg",checkItemList);
            return abnormalJson;
        }

        String recordId = String.valueOf(UUID.randomUUID());
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
        checkRecord.setUserId(questJson.getLong("userId"));
        checkRecord.setCheckPlace(questJson.getString("checkPlace"));
        checkRecord.setRecordTime(questJson.getDate("recordTime"));
        checkRecord.setCheckContent(questJson.getString("checkContent"));
        checkRecord.setCheckType(0);
        Integer tip = checkRecordMapper.insertCheckRecord(checkRecord);
        insertImg(questJson.getJSONArray("imgArray"),recordId);
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

    @Autowired
    private CheckRecordAbnormalMapper checkRecordAbnormalMapper;
    //巡检异常提交接口
    @PostMapping("/checkAbnormal")
    public JSONObject checkAbnormal(@RequestBody JSONObject questJson){
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
        JSONArray abnormalItemArray = questJson.getJSONArray("abnormalItemArray");

        String recordId = String.valueOf(UUID.randomUUID());
        for (int i = 0; i < itemArray.size(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            RecordItem recordItem = new RecordItem();
            recordItem.setRecordId(recordId);
            recordItem.setItemId(item.getLong("itemId"));
            recordItem.setItemIf(item.getInteger("itemIf"));
            for (int j = 0; j < abnormalItemArray.size(); j++) {
                JSONObject abnormalItem = abnormalItemArray.getJSONObject(j);
                if(item.getLong("itemId") == abnormalItem.getLong("itemId")){
                    recordItem.setItemAbnormal(0);
                    break;
                }else {
                    recordItem.setItemAbnormal(1);
                }
            }
            commonMapper.insertRecordItem(recordItem);
        }
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setRecordId(recordId);
        checkRecord.setUserId(questJson.getLong("userId"));
        checkRecord.setCheckPlace(questJson.getString("checkPlace"));
        checkRecord.setRecordTime(questJson.getDate("recordTime"));
        checkRecord.setCheckContent(questJson.getString("checkContent"));
        checkRecord.setCheckType(0);
        Integer tip = checkRecordMapper.insertCheckRecord(checkRecord);

        CheckRecordAbnormal abnormal = new CheckRecordAbnormal();
        abnormal.setRecordId(recordId);
        abnormal.setUserId(questJson.getLong("userId"));
        abnormal.setCheckPlace(questJson.getString("checkPlace"));
        abnormal.setRecordTime(questJson.getDate("recordTime"));
        abnormal.setCheckContent(questJson.getString("checkContent"));
        abnormal.setRemark(questJson.getString("remark"));
        abnormal.setRemarkSpecial(questJson.getString("remarkSpecial"));
        abnormal.setAbnormalLev(questJson.getInteger("abnormalLev"));
        abnormal.setEventType(0);
        tip = checkRecordAbnormalMapper.insertCheckRecordAbnormal(abnormal);

        insertImg(questJson.getJSONArray("imgArray"),recordId);
        insertImgAbnormal(questJson.getJSONArray("imgArrayAbnormal"),recordId);
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

    @Autowired
    private CheckXgRecordMapper checkXgRecordMapper;
    //巡更提交接口
    @PostMapping("/submitXgData")
    public JSONObject submitXgData(@RequestBody JSONObject questJson){
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

        String recordId = String.valueOf(UUID.randomUUID());
        CheckXgRecord checkXgRecord = new CheckXgRecord();
        checkXgRecord.setRecordId(recordId);
        checkXgRecord.setUserId(questJson.getLong("userId"));
        checkXgRecord.setCheckContent(questJson.getString("checkContent"));
        checkXgRecord.setRecordTime(questJson.getDate("recordTime"));
        checkXgRecord.setCheckPlace(questJson.getString("checkPlace"));
        Integer tip = checkXgRecordMapper.insertCheckXgRecord(checkXgRecord);

        insertImg2(questJson.getJSONArray("imgArray"),recordId);
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

    //插入巡检图片
    @Async("threadPoolTaskExecutor")
    public void insertImg(JSONArray imgArray,String recordId){
        CheckRecord record = new CheckRecord();
        record.setRecordId(recordId);
        List<CheckRecord> recordList = checkRecordMapper.selectCheckRecordList(record);
        Long cordId = recordList.get(0).getId();
        CheckRecordAbnormal checkRecordAbnormal = new CheckRecordAbnormal();
        checkRecordAbnormal.setRecordId(recordId);
        HashMap abnormal = null;
        Long abnormalId = null;
        abnormal = checkRecordAbnormalMapper.selectCheckAbnormalByRecordId2(checkRecordAbnormal);
        if(abnormal != null){
            abnormalId = (Long) abnormal.get("id");
        }
        for (int i = 0; i < imgArray.size(); i++) {
            String itemImg = imgArray.getString(i);
            RecordImg recordImg = new RecordImg();
            recordImg.setRecordId(cordId);
            recordImg.setItemImg(itemImg);
            recordImg.setImgType(0);
            String imgPath = RuoYiConfig.getApiImgUrl()+"/"+itemImg;
            String imgBase64 = ImgToBase64.requestUrlToBase64(imgPath);
            recordImg.setImgBase64(imgBase64);
            if(abnormal != null){
                recordImg.setAbnormalId(abnormalId);
            }
            commonMapper.insertRecordImg(recordImg);
        }
    }

    //插入巡检异常图片
    @Async("threadPoolTaskExecutor")
    public void insertImgAbnormal(JSONArray imgArray,String recordId){
        CheckRecord record = new CheckRecord();
        record.setRecordId(recordId);
        List<CheckRecord> recordList = checkRecordMapper.selectCheckRecordList(record);
        Long cordId = recordList.get(0).getId();
        CheckRecordAbnormal checkRecordAbnormal = new CheckRecordAbnormal();
        checkRecordAbnormal.setRecordId(recordId);
        HashMap abnormal = checkRecordAbnormalMapper.selectCheckAbnormalByRecordId2(checkRecordAbnormal);
        Long abnormalId = (Long) abnormal.get("id");
        for (int i = 0; i < imgArray.size(); i++) {
            String itemImg = imgArray.getString(i);
            RecordImg recordImg = new RecordImg();
            recordImg.setRecordId(cordId);
            recordImg.setAbnormalId(abnormalId);
            recordImg.setItemImg(itemImg);
            recordImg.setImgType(1);
            String imgPath = RuoYiConfig.getApiImgUrl()+"/"+itemImg;
            String imgBase64 = ImgToBase64.requestUrlToBase64(imgPath);
            recordImg.setImgBase64(imgBase64);
            commonMapper.insertRecordImg(recordImg);
        }
    }


    //插入巡更图片
    @Async("threadPoolTaskExecutor")
    public void insertImg2(JSONArray imgArray,String recordId){
        CheckXgRecord record = new CheckXgRecord();
        record.setRecordId(recordId);
        List<CheckXgRecord> recordList = checkXgRecordMapper.selectCheckXgRecordList(record);
        Long cordId = recordList.get(0).getId();
        for (int i = 0; i < imgArray.size(); i++) {
            String itemImg = imgArray.getString(i);
            RecordImg2 recordImg = new RecordImg2();
            recordImg.setRecordId(cordId);
            recordImg.setItemImg(itemImg);
            commonMapper.insertRecordImg2(recordImg);
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

    //根据用户ID查询所有巡检异常
    @PostMapping("/getCheckAbnormal")
    public List getCheckAbnormal(@RequestBody JSONObject questJson){
        Long userId = questJson.getLong("userId");
        CheckUser checkUser = checkUserMapper.selectCheckUserById(userId);
        Integer userRole = checkUser.getUserRole();
        String userDept = checkUser.getUserDept();
        List<HashMap> list = null;
        if(userRole == 0){
            //巡检员可以看到他自己的巡检记录
            Integer type = questJson.getInteger("type");
            CheckRecordAbnormal abnormal = new CheckRecordAbnormal();
            abnormal.setUserId(userId);
            if(type == 0){
                abnormal.setEventType(0);
            }if(type == 1){
                abnormal.setEventType(1);
            }if(type == 2){
                abnormal.setEventType(2);
            }if(type == 3){
                abnormal.setEventType(3);
            }if(type == 4){
                abnormal.setEventType(4);
            }
            list = checkRecordAbnormalMapper.selectCheckAbnormalList(abnormal);
        }else if(userRole == 2){
            //科室主任可以看到他科室的巡检记录
            Integer type = questJson.getInteger("type");
            if(userDept.contains(",")){
                String[] placeIdsStr = userDept.split(",");
                Long[] placeIds = new Long[placeIdsStr.length];
                for (int i = 0; i < placeIdsStr.length; i++) {
                    placeIds[i] = Long.valueOf(placeIdsStr[i]);
                }
                List<HashMap> maps =  commonMapper.selectPlaceId(placeIds);
                String[] place = new String[maps.size()];
                for (int i = 0; i < maps.size(); i++) {
                    place[i] = (String) maps.get(i).get("place_id");
                }
                list = checkRecordAbnormalMapper.selectByPlaceId(place);
                if(type == 0){
                    for (int i = 0; i < list.size(); i++) {
                        HashMap map = list.get(i);
                        Integer eventType = (Integer) map.get("event_type");
                        if(eventType != 0){
                            list.remove(i);
                            i--;
                        }
                    }
                }else if(type == 1){
                    for (int i = 0; i < list.size(); i++) {
                        HashMap map = list.get(i);
                        Integer eventType = (Integer) map.get("event_type");
                        if(eventType != 1){
                            list.remove(i);
                            i--;
                        }
                    }
                }else if(type == 2){
                    for (int i = 0; i < list.size(); i++) {
                        HashMap map = list.get(i);
                        Integer eventType = (Integer) map.get("event_type");
                        if(eventType != 2){
                            list.remove(i);
                            i--;
                        }
                    }
                }else if(type == 3){
                    for (int i = 0; i < list.size(); i++) {
                        HashMap map = list.get(i);
                        Integer eventType = (Integer) map.get("event_type");
                        if(eventType != 3){
                            list.remove(i);
                            i--;
                        }
                    }
                }else if(type == 4){
                    for (int i = 0; i < list.size(); i++) {
                        HashMap map = list.get(i);
                        Integer eventType = (Integer) map.get("event_type");
                        if(eventType != 4){
                            list.remove(i);
                            i--;
                        }
                    }
                }
            }else {
                CheckPlace checkPlace = checkPlaceMapper.selectCheckPlaceById(Long.valueOf(userDept));
                CheckRecordAbnormal abnormal = new CheckRecordAbnormal();
                abnormal.setCheckPlace(checkPlace.getPlaceId());
                if(type == 0){
                    abnormal.setEventType(0);
                }else if(type == 1){
                    abnormal.setEventType(1);
                }else if(type == 2){
                    abnormal.setEventType(2);
                }else if(type == 3){
                    abnormal.setEventType(3);
                }else if(type == 4){
                    abnormal.setEventType(4);
                }
                list = checkRecordAbnormalMapper.selectCheckAbnormalList(abnormal);
            }
        }
        else if(userRole == 4){
            CheckRecordAbnormal abnormal = new CheckRecordAbnormal();
            Integer type = questJson.getInteger("type");
            if(type == 0){
                abnormal.setEventType(0);
            }else if(type == 1){
                abnormal.setEventType(1);
            }else if(type == 2){
                abnormal.setEventType(2);
            }else if(type == 3){
                abnormal.setEventType(3);
            }else if(type == 4){
                abnormal.setEventType(4);
            }
            list = checkRecordAbnormalMapper.selectCheckAbnormalList(abnormal);
        }else if(userRole == 5){
            list = checkRecordAbnormalMapper.selectCheckAbnormalList(new CheckRecordAbnormal());
            for (int i = 0; i < list.size(); i++) {
                HashMap map = list.get(i);
                String functionOffice = (String) map.get("function_office");

                if(functionOffice != null){
                    if(!functionOffice.contains("0")){
                        list.remove(i);
                        i--;
                    }
                }else {
                    list.remove(i);
                    i--;
                }

            }
        }else if(userRole == 6){
            list = checkRecordAbnormalMapper.selectCheckAbnormalList(new CheckRecordAbnormal());
            for (int i = 0; i < list.size(); i++) {
                HashMap map = list.get(i);
                String functionOffice = (String) map.get("function_office");
                if(functionOffice != null){
                    if(!functionOffice.contains("1")){
                        list.remove(i);
                        i--;
                    }else {
                        list.remove(i);
                        i--;
                    }
                }

            }
        }else if(userRole == 7){
            list = checkRecordAbnormalMapper.selectCheckAbnormalList(new CheckRecordAbnormal());
            for (int i = 0; i < list.size(); i++) {
                HashMap map = list.get(i);
                String functionOffice = (String) map.get("function_office");
                if(functionOffice != null){
                    if(!functionOffice.contains("2")){
                        list.remove(i);
                        i--;
                    }else {
                        list.remove(i);
                        i--;
                    }
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            HashMap map = list.get(i);
            if(!map.containsKey("place_name")){
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    @PostMapping("/testaa")
    public List<HashMap> testaa(@RequestBody Long[] placeIds){
        return commonMapper.selectPlaceId(placeIds);
    }

    //根据巡检异常记录ID查询详情
    @PostMapping("/getAbnormalDetail")
    public JSONObject getAbnormalDetail(@RequestBody JSONObject questJson){
        String recordId = questJson.getString("recordId");
        CheckRecordAbnormal abnormal = new CheckRecordAbnormal();
        abnormal.setRecordId(recordId);
        List<HashMap> recordList = checkRecordAbnormalMapper.selectCheckAbnormalByRecordId(abnormal);
        HashMap map = recordList.get(0);

        CheckRecord record = new CheckRecord();
        record.setRecordId(recordId);
        List<HashMap> recordList2 = checkRecordMapper.selectCheckRecordByRecordId(record);
        HashMap recordMap = recordList2.get(0);

        List<HashMap> recordItems = commonMapper.selectRecordItems(recordId);
        List<HashMap> recordAbnormalItems = commonMapper.selectAbnormalItems(recordId);
        List<HashMap> recordImgs = commonMapper.selectRecordImgs((Long) recordMap.get("id"));
        List<HashMap> abnormalImgs = commonMapper.selectAbnormalImgs((Long) recordMap.get("id"));
        for (int i = 0; i < recordImgs.size(); i++) {
            HashMap map2 = recordImgs.get(i);
            String placeImg = (String) recordImgs.get(i).get("item_img");
            map2.put("item_img",RuoYiConfig.getApiImgUrl()+"/"+placeImg);
        }
        for (int i = 0; i < abnormalImgs.size(); i++) {
            HashMap map3 = abnormalImgs.get(i);
            String placeImg = (String) abnormalImgs.get(i).get("item_img");
            map3.put("item_img",RuoYiConfig.getApiImgUrl()+"/"+placeImg);
        }
        JSONObject reJson = new JSONObject();
        reJson.put("record",recordList.get(0));
        reJson.put("items",recordItems);
        reJson.put("abnormalItems",recordAbnormalItems);
        reJson.put("imgs",recordImgs);
        reJson.put("abnormalImgs",abnormalImgs);

        return reJson;
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
            map2.put("item_img",RuoYiConfig.getApiImgUrl()+"/"+placeImg);
        }
        JSONObject reJson = new JSONObject();
        reJson.put("record",recordList.get(0));
        reJson.put("items",recordItems);
        reJson.put("imgs",recordImgs);

        return reJson;
    }

    //根据巡检异常记录ID更新巡检异常记录
    @PostMapping("/updateAbnormalHandle")
    public JSONObject updateAbnormalHandle(@RequestBody JSONObject questJson){
        String recordId = questJson.getString("recordId");
        Integer eventType = questJson.getInteger("eventType");
        Integer abnormalLev = questJson.getInteger("abnormalLev");
        String handleFlow = questJson.getString("handleFlow");
//        CheckUser checkUser = checkUserMapper.selectCheckUserById(userId);

        CheckRecordAbnormal abnormal = new CheckRecordAbnormal();
        abnormal.setRecordId(recordId);
        abnormal.setHandleMethod(questJson.getString("handleMethod"));
        abnormal.setHandleResult(questJson.getString("handleResult"));
        abnormal.setEventType(eventType);
        abnormal.setHandleFlow(getHandleFlow(abnormalLev,eventType,handleFlow));
        Integer tip = checkRecordAbnormalMapper.updateCheckRecordAbnormalByRecordId(abnormal);
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

    //根据userId、handleFlow、eventType返回应该更新的handleFlow
    public String getHandleFlow(Integer abnormalLev, Integer eventType, String handleFlow){
        if(abnormalLev == 0 && eventType == 2){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date nowDate = new Date();
            String strDate = sdf.format(nowDate);
            handleFlow = "1,"+strDate;
            return handleFlow;
        }else if(abnormalLev == 1 && eventType == 2) {
            String[] strAry = handleFlow.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date nowDate = new Date();
            String strDate = sdf.format(nowDate);
            handleFlow = strAry[0] + "," + strAry[1] + ",1," + strDate;
            return handleFlow;
        }else if(abnormalLev == 2 && eventType == 2) {
            String[] strAry = handleFlow.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date nowDate = new Date();
            String strDate = sdf.format(nowDate);
            handleFlow = strAry[0] + "," + strAry[1] + ",1," + strDate;
            return handleFlow;
        }
        return null;
    }


    //测试插入巡检图片
    @PostMapping("/testInsert")
    public void testInsert()throws Exception{
        CheckPlace checkPlace = new CheckPlace();
        checkPlace.setPlaceName("五病区");
        String placeId = String.valueOf(UUID.randomUUID());
        String url = RuoYiConfig.getWebUrl()+"/"+"?placeId="+placeId;
        String logoPath = RuoYiConfig.getLogoUrl();
        String destPath = RuoYiConfig.getCheckImg();
        String fileName = QRCodeUtil.encode(url,logoPath,destPath,true);
        checkPlace.setPlaceId(placeId);
        checkPlace.setPlaceImg(fileName);
        checkPlaceMapper.insertCheckPlace(checkPlace);
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

    //测试把巡检项的等级和异常都插上
    @PostMapping("/insertItem")
    public void insertItem(){
        List<CheckItem> list = checkItemMapper.selectCheckItemList(new CheckItem());
        for (int i = 0; i < list.size(); i++) {
            CheckItem item  =list.get(i);
            item.setItemAbnormal(0);
            item.setAbnormalLev(0);
            checkItemMapper.updateCheckItem(item);
        }
    }
}
