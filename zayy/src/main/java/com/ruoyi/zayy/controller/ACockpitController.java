package com.ruoyi.zayy.controller;


import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.zayy.domain.CheckFunctionOffice;
import com.ruoyi.zayy.domain.CheckRecordAbnormal;
import com.ruoyi.zayy.mapper.CheckRecordAbnormalMapper;
import com.ruoyi.zayy.mapper.CommonMapper;
import com.ruoyi.zayy.service.ICheckFunctionOfficeService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
}
