package com.ruoyi.zayy.controller;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.zayy.domain.*;
import com.ruoyi.zayy.mapper.*;
import com.ruoyi.zayy.service.ICheckFunctionOfficeService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Handler;


@RestController
@RequestMapping("/cockpitApi")//驾驶舱接口
public class ACockpitController{

    @Autowired
    private ICheckFunctionOfficeService checkFunctionOfficeService;
    @PostMapping("/functionOffice")//职能科室
    public List<?> functionOffice(){
        return checkFunctionOfficeService.selectCheckFunctionOfficeList(new CheckFunctionOffice());
    }
    @Autowired
    private CheckRecordAbnormalMapper checkRecordAbnormalMapper;

    @PostMapping("/allCheckAbnormal")//所有的巡检异常
    public JSONObject allCheckAbnormal(@RequestBody JSONObject questJson){
        Integer pageSize = questJson.getInteger("pageSize");
        Integer page = questJson.getInteger("page");
        PageHelper.startPage(page,pageSize);
        List<HashMap> list = checkRecordAbnormalMapper.selectCheckRecordAbnormalListCockpitApi(new CheckRecordAbnormal());
        PageInfo<HashMap> pageInfo = new PageInfo<>(list);
        JSONObject reJson = new JSONObject();
        reJson.put("data",pageInfo.getList());
        reJson.put("total",pageInfo.getTotal());
        return reJson;
    }

    @PostMapping("/dayCheckAbnormal")//当日巡检异常列表
    public JSONObject dayCheckAbnormal(@RequestBody JSONObject questJson){
        Integer pageSize = questJson.getInteger("pageSize");
        Integer page = questJson.getInteger("page");
        Date now = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Date startDate = cal1.getTime();
        Date endDate = new Date(startDate.getTime() + (24*60*60*1000));
        CheckRecordAbnormal checkRecordAbnormal = new CheckRecordAbnormal();
        checkRecordAbnormal.setStartDate(startDate);
        checkRecordAbnormal.setEndDate(endDate);
        PageHelper.startPage(page,pageSize);
        List<HashMap> list = checkRecordAbnormalMapper.selectAbnormalDay(checkRecordAbnormal);
        PageInfo<HashMap> pageInfo = new PageInfo<>(list);
        JSONObject reJson = new JSONObject();
        reJson.put("data",pageInfo.getList());
        reJson.put("total",pageInfo.getTotal());
        return reJson;
    }

    @PostMapping("/monthCheckAbnormal")//当月巡检异常列表
    public JSONObject monthCheckAbnormal(@RequestBody JSONObject questJson) throws ParseException {
        Integer pageSize = questJson.getInteger("pageSize");
        Integer page = questJson.getInteger("page");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = df.format(new Date());

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

        Date startDate = df.parse(firstDayOfMonth);
        Date endDate = df.parse(lastDayOfMonth);
        CheckRecordAbnormal checkRecordAbnormal = new CheckRecordAbnormal();
        checkRecordAbnormal.setStartDate(startDate);
        checkRecordAbnormal.setEndDate(endDate);
        PageHelper.startPage(page,pageSize);
        List<HashMap> list = checkRecordAbnormalMapper.selectAbnormalDay(checkRecordAbnormal);
        PageInfo<HashMap> pageInfo = new PageInfo<>(list);
        JSONObject reJson = new JSONObject();
        reJson.put("data",pageInfo.getList());
        reJson.put("total",pageInfo.getTotal());
        return reJson;
    }

    @Autowired
    CommonMapper commonMapper;

    @PostMapping("/getEventsNum")//获取累计事件处理数,累计事件总数
    public JSONObject getEventsNum() throws ParseException{
        Integer total = commonMapper.getCheckAbnormalAllCount();
        Integer processed = commonMapper.getCheckAbnormalAlreadyCount();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = df.format(new Date());

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

        Date startDate = df.parse(firstDayOfMonth);
        Date endDate = df.parse(lastDayOfMonth);
        CheckRecordAbnormal checkRecordAbnormal = new CheckRecordAbnormal();
        checkRecordAbnormal.setStartDate(startDate);
        checkRecordAbnormal.setEndDate(endDate);

        Integer monthTotal = commonMapper.getCheckAbnormalMonthAllCount(checkRecordAbnormal);
        Integer monthProcessed = commonMapper.getCheckAbnormalMonthAlreadyCount(checkRecordAbnormal);

        Date now = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Date startDate2 = cal1.getTime();
        Date endDate2 = new Date(startDate2.getTime() + (24*60*60*1000));
        CheckRecordAbnormal checkRecordAbnormal2 = new CheckRecordAbnormal();
        checkRecordAbnormal2.setStartDate(startDate2);
        checkRecordAbnormal2.setEndDate(endDate2);

        Integer dayTotal = commonMapper.getCheckAbnormalDayAllCount(checkRecordAbnormal2);
        Integer dayProcessed = commonMapper.getCheckAbnormalDayAlreadyCount(checkRecordAbnormal2);

        JSONObject reJson = new JSONObject();
        reJson.put("total",total);
        reJson.put("processed",processed);
        reJson.put("monthTotal",monthTotal);
        reJson.put("monthProcessed",monthProcessed);
        reJson.put("dayTotal",dayTotal);
        reJson.put("dayProcessed",dayProcessed);
        return reJson;
    }

    @PostMapping("/latestCheckAbnormal")//最新8条巡检异常事件
    public List<HashMap> latestCheckAbnormal(){
        List<HashMap> list = checkRecordAbnormalMapper.latestCheckAbnormal(new CheckRecordAbnormal());
        List<HashMap> list2 = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            HashMap map = list.get(i);
            map.put("type",0);
            Object timeObject = map.get("record_time");
            Date timeStr = (Date) timeObject;
            String time = simpleDateFormat.format(timeStr);
            map.put("record_time",time);
            list2.add(map);
        }
        return list2;
    }
    @Autowired
    CheckRecordMapper checkRecordMapper;
    @PostMapping("/getDetail")//根据recordId查询详情
    public JSONObject getDetail(@RequestBody JSONObject questJson){
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
        List<HashMap> abnormalFunction = commonMapper.selectRecordFunction(recordId);
        for (int i = 0; i < recordImgs.size(); i++) {
            HashMap map2 = recordImgs.get(i);
            String placeImg = (String) recordImgs.get(i).get("item_img");
            map2.put("item_img", RuoYiConfig.getApiImgUrl()+"/"+placeImg);
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
        reJson.put("abnormalFunction",abnormalFunction);
        return reJson;
    }
    @Autowired
    private RecordSmogAlarmMapper recordSmogAlarmMapper;
    @Autowired
    private RecordAjMapper recordAjMapper;
    @PostMapping("/importanceEvent")//重要事件
    public JSONArray importanceEvent() throws Exception {
        JSONArray reArray = new JSONArray();
        Date now = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Date startDate = cal1.getTime();
        Date endDate = new Date(startDate.getTime() + (48*60*60*1000));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        RecordSmogAlarm smogAlarm = new RecordSmogAlarm();
        smogAlarm.setStartDate(startDate);
        smogAlarm.setEndDate(endDate);
        List<RecordSmogAlarm> smogAlarmList = recordSmogAlarmMapper.selectDaySmog(smogAlarm);

        for (int i = 0; i < smogAlarmList.size(); i++) {
            RecordSmogAlarm smogAlarm1 = smogAlarmList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",1);
            jsonObject.put("eventId",smogAlarm1.getEventId());
            String time = simpleDateFormat.format(smogAlarm1.getHappenTime());
            jsonObject.put("time",time);
            jsonObject.put("content","消控室后弱电间烟感报警");
            reArray.add(jsonObject);
        }

        RecordAj recordAj = new RecordAj();
        recordAj.setStartDate(startDate);
        recordAj.setEndDate(endDate);
        recordAj.setSecurityType(20);
        List<RecordAj> recordAjFallList = recordAjMapper.selectDayAj(recordAj);
        for (int i = 0; i < recordAjFallList.size(); i++) {
            RecordAj recordAj1 = recordAjFallList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",2);
            jsonObject.put("eventId",recordAj1.getId());
            String time = simpleDateFormat.format(recordAj1.getSecurityTime());
            jsonObject.put("time",time);
            jsonObject.put("content","门诊三楼北通道");
            reArray.add(jsonObject);
        }

        recordAj.setSecurityType(40);
        List<RecordAj> recordAjRunList = recordAjMapper.selectDayAj2(recordAj);
        for (int i = 0; i < recordAjRunList.size(); i++) {
            RecordAj recordAj1 = recordAjRunList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",3);
            jsonObject.put("eventId",recordAj1.getId());
            String time = simpleDateFormat.format(recordAj1.getSecurityTime());
            jsonObject.put("time",time);
            jsonObject.put("content","门诊三楼北通道");
            reArray.add(jsonObject);
        }

        List<HashMap> blackList = getBlackList();
        for (int i = 0; i < blackList.size(); i++) {
            HashMap map = blackList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",4);
            jsonObject.put("eventId",map.get("id"));
            Object timeObject = map.get("access_time");
            Date timeStr = (Date) timeObject;
            String time = simpleDateFormat.format(timeStr);
            jsonObject.put("time",time);
            jsonObject.put("content",map.get("user_name"));
            reArray.add(jsonObject);
        }

        reArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("time") ).reversed());
        return reArray;
    }

    @PostMapping("/test")
    public void test(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RecordAj recordAj = new RecordAj();
        recordAj.setSecurityType(20);
        List<RecordAj> recordAjFallList = recordAjMapper.selectDayAj(recordAj);
        for (int i = 0; i < recordAjFallList.size(); i++) {
            RecordAj recordAj1 = recordAjFallList.get(i);
            String time = simpleDateFormat.format(recordAj1.getSecurityTime());
            System.out.println(recordAj1.getId()+"---"+time);
        }
    }

    @PostMapping("/getEventDetail")
    public Object getEventDetail(@RequestBody JSONObject questJson){
        Integer type = questJson.getInteger("type");//1烟感报警,2/3安检报警,4黑名单报警
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (type){
            case 1:{
                Long eventId = questJson.getLong("eventId");
                return recordSmogAlarmMapper.selectRecordSmogAlarmById(eventId);
            }
            case 2:
            case 3:{
                Long eventId = questJson.getLong("eventId");
                return recordAjMapper.selectRecordAjById(eventId);
            }
            case 4:{
                Integer id = questJson.getInteger("eventId");
                HashMap map = commonMapper.getBlackDetail(id);
                Object timeObject = map.get("access_time");
                Date timeStr = (Date) timeObject;
                String time = simpleDateFormat.format(timeStr);
                String userFaceImg = (String) map.get("user_face_img");
                map.put("access_time",time);
                map.put("user_face_img",RuoYiConfig.getBlackImgUrl()+userFaceImg);
                return map;
            }
        }
        return null;
    }

    @PostMapping("/submitAbnormal")//提交巡检异常
    public JSONObject submitAbnormal(@RequestBody JSONObject questJson){
        String recordId = questJson.getString("recordId");
        String handleMethod = questJson.getString("handleMethod");
        String officeIds = questJson.getString("officeIds");
        Integer abnormalLev = questJson.getInteger("abnormalLev");
        CheckRecordAbnormal abnormal = new CheckRecordAbnormal();
        abnormal.setRecordId(recordId);
        abnormal.setHandleMethod(handleMethod);
        abnormal.setEventType(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        if(abnormalLev == 0){//黄色告警
            abnormal.setHandleFlow("1,"+date);
        }else {
            abnormal.setHandleFlow("1,"+date+"0,null");
        }
        abnormal.setHandleFlow("1,"+date);
        checkRecordAbnormalMapper.updateCheckRecordAbnormalByRecordId(abnormal);

        String[] placeIdsStr = officeIds.split(",");
        for (int i = 0; i < placeIdsStr.length; i++) {
            HashMap map = new HashMap();
            map.put("recordId",recordId);
            map.put("functionId",placeIdsStr[i]);
            commonMapper.insertRecordFunction(map);
        }
        JSONObject reJson = new JSONObject();
        reJson.put("code",200);
        reJson.put("msg","提交成功");
        return reJson;
    }

    @PostMapping("/getBlackList")
    public List<HashMap> getBlackList() throws Exception { //从另一个数据库查询黑名单列表
        Date now = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Date startDate = cal1.getTime();
        Date endDate = new Date(startDate.getTime() + (24*60*60*1000));
        return commonMapper.getBlackList(startDate,endDate);
    }

}
